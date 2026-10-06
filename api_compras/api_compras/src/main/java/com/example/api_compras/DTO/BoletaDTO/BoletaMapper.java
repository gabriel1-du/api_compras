package com.example.api_compras.DTO.BoletaDTO;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.api_compras.DTO.BoletaDTO.getBoletaDTO.DetalleProductoDTO;
import com.example.api_compras.Model.Boleta;
import com.example.api_compras.Model.MedioPago;
import com.example.api_compras.Repository.MedioPagoRepository;

@Component 
public class BoletaMapper {

    @Autowired 
    private MedioPagoRepository medioPagoRepo;

    public getBoletaDTO toGetDTO(Boleta boleta, List<DetalleProductoDTO> detalles) {
        if (boleta == null) {
            return null;
        }

        getBoletaDTO dto = new getBoletaDTO();

        dto.setId_boleta(boleta.getId_boleta());
        dto.setNombre(boleta.getNombre_comprador());
        dto.setTotal(boleta.getMonto_total());
        dto.setFecha(boleta.getFecha_emision());

        // Extraemos el nombre del medio de pago directamente de la entidad relacionada
        if (boleta.getMedioPago() != null) {
            dto.setMedio_pago(boleta.getMedioPago().getNombre_medio());
        }

        // Asignamos la lista de productos que el Service extrajo del carrito
        dto.setProducto(detalles);

        return dto;
    }


    public Boleta toEntityFromSaveDTO(saveBoletaDTO dto) {
        if (dto == null) {
            return null;
        }

        // Validar que el medio de pago exista
        MedioPago medio = medioPagoRepo.findById(dto.getId_medio_pago())
                .orElseThrow(() -> new RuntimeException("Medio de pago no encontrado con ID: " + dto.getId_medio_pago()));

        Boleta boleta = new Boleta();
        
        boleta.setId_usuario(dto.getId_usuario());
        boleta.setMedioPago(medio);
        boleta.setRut_comprador(dto.getRut_comprador());
        boleta.setNombre_comprador(dto.getNombre_comprador());


        return boleta;
    }
}
