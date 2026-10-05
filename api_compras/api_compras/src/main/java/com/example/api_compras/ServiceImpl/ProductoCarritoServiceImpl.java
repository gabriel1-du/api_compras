package com.example.api_compras.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_compras.DTO.ProductoCarritoDTO.ProductoCarritoMapper;
import com.example.api_compras.DTO.ProductoCarritoDTO.getProductoCarritoDTO;
import com.example.api_compras.DTO.ProductoCarritoDTO.putCarritoDTO;
import com.example.api_compras.DTO.ProductoCarritoDTO.saveCarritoProductoCartDTO;
import com.example.api_compras.DTO.RestClientDTO.HistorietaExternoDTO;
import com.example.api_compras.Model.Carrito;
import com.example.api_compras.Model.ProductoCarrito;
import com.example.api_compras.Repository.CarritoRepository;
import com.example.api_compras.Repository.ProductoCarritoRepository;
import com.example.api_compras.RestClient.HistorietaClient;
import com.example.api_compras.Service.ProductoCarritoService;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProductoCarritoServiceImpl implements ProductoCarritoService {

    @Autowired 
    private final ProductoCarritoRepository repo;

    @Autowired 
    private final CarritoRepository repoCarrito;

    @Autowired 
    private final HistorietaClient histClient;

    private final ProductoCarritoMapper mapper;

    //Metodos get
   public getProductoCarritoDTO getProductoCarritoByid(Long id_producto) {

        ProductoCarrito producto = repo.findById(id_producto)
                .orElseThrow(() -> new RuntimeException("Producto de carrito no encontrado con id: " + id_producto));
        
        // Buscaos la historieta por el id del producto usando el Rest Client
        HistorietaExternoDTO histoExtern = histClient.obtenerHistorietaPorId(producto.getId_producto());

        //Devolvemos con el dto
        return mapper.toGetProdCarritoDTO(producto, histoExtern);
    } 


    //fin metodos post
    public List<getProductoCarritoDTO> getAllProductoCarrito() {
        return repo.findAll()
                .stream()
                .map(prodCarrito -> {

                    HistorietaExternoDTO histoExtern = histClient.obtenerHistorietaPorId(prodCarrito.getId_producto());
                    

                    return mapper.toGetProdCarritoDTO(prodCarrito, histoExtern);
                })
                .collect(Collectors.toList());
    }



    public getProductoCarritoDTO saveProductoCarrito(saveCarritoProductoCartDTO dto) {
        try {
            
            Carrito carrito = repoCarrito.findById(dto.getId_carrito())
                    .orElse(null);

            
            HistorietaExternoDTO histoExtern = histClient.obtenerHistorietaPorId(dto.getId_producto());

            // Usar el mapper para validar y convertir a Entidad
            ProductoCarrito productoNuevo = mapper.toEntityFromSaveDTO(dto, histoExtern, carrito);


            ProductoCarrito productoGuardado = repo.save(productoNuevo);

  
            return mapper.toGetProdCarritoDTO(productoGuardado, histoExtern);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el producto en el carrito: " + e.getMessage());
        }
    }

    public getProductoCarritoDTO putProductoCarrito(Long id_producto_carrito, putCarritoDTO putDto) {
        
        //comprobacion carrito
        ProductoCarrito productoExistente = repo.findById(id_producto_carrito)
                .orElseThrow(() -> new RuntimeException("Producto de carrito no encontrado con el ID: " + id_producto_carrito));

    
        mapper.updateEntityFromPutDTO(putDto, productoExistente);

        // Guardar cambios
        ProductoCarrito productoActualizado = repo.save(productoExistente);

        // Consulta hacia client 
        HistorietaExternoDTO histoExtern = histClient.obtenerHistorietaPorId(productoActualizado.getId_producto());

    
        return mapper.toGetProdCarritoDTO(productoActualizado, histoExtern);
    }


    public void deleteProductoCarrito(Long id_producto_carrito) {
        // 1. Verificar si el producto del carrito existe en la base de datos local
        ProductoCarrito producto_del = repo.findById(id_producto_carrito)
                .orElseThrow(() -> new RuntimeException("Producto de carrito no encontrado con el ID: " + id_producto_carrito));

        // 2. Eliminar el registro
        repo.delete(producto_del);
    }

}
