package com.example.api_compras.DTO.ProductoCarritoDTO;

import org.springframework.stereotype.Component;

import com.example.api_compras.DTO.RestClientDTO.HistorietaExternoDTO;
import com.example.api_compras.Model.Carrito;
import com.example.api_compras.Model.ProductoCarrito;

@Component 
public class ProductoCarritoMapper {
    

    public getProductoCarritoDTO  toGetProdCarritoDTO(ProductoCarrito prodCarrito, HistorietaExternoDTO hist) {
        
        if (prodCarrito == null) {
            return null;
        }

        // Verificación de existencia en el microservicio externo
        if (hist == null) {
            throw new RuntimeException("ID de historieta inexistente");
        }

        //Inicio de llenado de datos al dto
        getProductoCarritoDTO  dto = new getProductoCarritoDTO();

        // Id's
        dto.setId_producto_carrito(prodCarrito.getId_producto_carrito());
  
        if (prodCarrito.getCarrito() != null) {
            dto.setId_carrito(prodCarrito.getCarrito().getId_carrito()); 
        }

        //datos externos
        dto.setId_historieta(hist.getId_historieta());
        dto.setNombre_historieta(hist.getNombre_historieta());

        // datos propios 
        dto.setCantidad(prodCarrito.getCantidad());
        dto.setPrecio_unitario(prodCarrito.getPrecio_unitario());
        
        return dto;
    };

    public ProductoCarrito toEntityFromSaveDTO(saveCarritoProductoCartDTO dto, HistorietaExternoDTO hist, Carrito carritoExistente) {

        if (dto == null) {
            return null;
        }

        // Verificación de que la historieta externa exista
        if (hist == null) {
            throw new RuntimeException("ID de historieta inexistente: " + dto.getId_producto());
        }

        // Verificación de que el carrito exista (puedes validar esto en el servicio o directamente aquí)
        if (carritoExistente == null) {
            throw new RuntimeException("ID de carrito inexistente: " + dto.getId_carrito());
        }

        ProductoCarrito prodCarrito = new ProductoCarrito();

        // Asignación de la relación con Carrito
        prodCarrito.setCarrito(carritoExistente);

        // Asignación de la referencia lógica del producto (historieta)
        prodCarrito.setId_producto(dto.getId_producto());

        // Asignación de datos propios
        prodCarrito.setCantidad(dto.getCantidad());
        prodCarrito.setPrecio_unitario(dto.getPrecio_unitario());

        return prodCarrito;
    }

    public void updateEntityFromPutDTO(putCarritoDTO dto, ProductoCarrito prodCarritoExistente) {
        if (dto == null || prodCarritoExistente == null) {
            return;
        }

        if (dto.getCantidad() != null) {
            prodCarritoExistente.setCantidad(dto.getCantidad());
        }

        if (dto.getPrecio_unitario() != null) {
            prodCarritoExistente.setPrecio_unitario(dto.getPrecio_unitario());
        }
    }
    
};


