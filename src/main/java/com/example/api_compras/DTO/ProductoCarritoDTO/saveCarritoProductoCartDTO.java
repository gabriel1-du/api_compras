package com.example.api_compras.DTO.ProductoCarritoDTO;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;


import lombok.Data;

@Data 
@Component 
public class saveCarritoProductoCartDTO {

    private Long id_carrito;
    private Long id_producto;
    private Integer cantidad;
    private BigDecimal precio_unitario;



    
}
