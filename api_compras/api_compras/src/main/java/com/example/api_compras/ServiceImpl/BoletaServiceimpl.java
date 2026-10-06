package com.example.api_compras.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_compras.DTO.BoletaDTO.BoletaMapper;
import com.example.api_compras.DTO.BoletaDTO.getBoletaDTO;
import com.example.api_compras.DTO.BoletaDTO.getBoletaDTO.DetalleProductoDTO;
import com.example.api_compras.DTO.BoletaDTO.saveBoletaDTO;
import com.example.api_compras.DTO.RestClientDTO.HistorietaExternoDTO;
import com.example.api_compras.Model.Boleta;
import com.example.api_compras.Model.ProductoCarrito;
import com.example.api_compras.Repository.BoletaRepository;
import com.example.api_compras.Repository.MedioPagoRepository;
import com.example.api_compras.Repository.ProductoCarritoRepository;
import com.example.api_compras.RestClient.HistorietaClient;
import com.example.api_compras.Service.BoletaService;

@Service 
public class BoletaServiceimpl implements BoletaService {

    //imports
    @Autowired 
    private  BoletaRepository boletaRepo;
    
    @Autowired 
    private ProductoCarritoRepository productoCarritoRepo;

    @Autowired 
    private MedioPagoRepository medioPagoRepo;

    @Autowired 
    private HistorietaClient histClient;

    @Autowired 
    private BoletaMapper mapper;

    // Método auxiliar para obtener la lista de detalles y sus nombres desde el microservicio
    private List<DetalleProductoDTO> obtenerDetallesDelCarrito(Long id_carrito) {
        // Asume que tienes un método en ProductoCarritoRepository para buscar por carrito
        List<ProductoCarrito> productos = productoCarritoRepo.findByCarritoId(id_carrito); 
        List<DetalleProductoDTO> detalles = new ArrayList<>();

        for (ProductoCarrito prod : productos) {
            DetalleProductoDTO detalle = new DetalleProductoDTO();
            detalle.setCantidad(prod.getCantidad());
            detalle.setPrecio_unitario(prod.getPrecio_unitario());

            // Buscar el nombre real de la historieta
            HistorietaExternoDTO hist = histClient.obtenerHistorietaPorId(prod.getId_producto());
            detalle.setNombre_producto(hist != null ? hist.getNombre_historieta() : "Producto no disponible");
            
            detalles.add(detalle);
        }
        return detalles;
    }

    @Override
    public List<getBoletaDTO> getAllBoletas() {
        return boletaRepo.findAll().stream().map(boleta -> {
            List<DetalleProductoDTO> detalles = obtenerDetallesDelCarrito(boleta.getId_carrito());
            return mapper.toGetDTO(boleta, detalles);
        }).collect(Collectors.toList());
    }

    @Override
    public getBoletaDTO getBoletaById(Long id_boleta) {
        Boleta boleta = boletaRepo.findById(id_boleta)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada con ID: " + id_boleta));

        List<DetalleProductoDTO> detalles = obtenerDetallesDelCarrito(boleta.getId_carrito());
        return mapper.toGetDTO(boleta, detalles);
    }

    @Override
    public getBoletaDTO saveBoleta(saveBoletaDTO dto) {
        try {
            // Obtener detalles
            List<DetalleProductoDTO> detalles = obtenerDetallesDelCarrito(dto.getId_carrito());
            if (detalles.isEmpty()) {
                throw new RuntimeException("El carrito está vacío o no existe.");
            }

            // 2. Calcular el total de forma automática
            BigDecimal totalCalculado = BigDecimal.ZERO;
            for (DetalleProductoDTO d : detalles) {
                BigDecimal subtotal = d.getPrecio_unitario().multiply(new BigDecimal(d.getCantidad()));
                totalCalculado = totalCalculado.add(subtotal);
            }

            // 3. Mapear y asignar valores generados por el backend
            Boleta nuevaBoleta = mapper.toEntityFromSaveDTO(dto);
            nuevaBoleta.setId_carrito(dto.getId_carrito()); // Guardamos la referencia al carrito
            nuevaBoleta.setMonto_total(totalCalculado);
            nuevaBoleta.setFecha_emision(LocalDateTime.now());

            Boleta boletaGuardada = boletaRepo.save(nuevaBoleta);

            return mapper.toGetDTO(boletaGuardada, detalles);

        } catch (Exception e) {
            throw new RuntimeException("Error al generar la boleta: " + e.getMessage());
        }
    }

    

    @Override
    public void deleteBoleta(Long id_boleta) {
        Boleta boleta = boletaRepo.findById(id_boleta)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada para eliminar con ID: " + id_boleta));
        boletaRepo.delete(boleta);
    }
}
