package com.example.api_compras.DTO.ProductoCarritoDTO;

import org.springframework.stereotype.Component;

import com.example.api_compras.DTO.RestClientDTO.HistorietaExternoDTO;
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
};
