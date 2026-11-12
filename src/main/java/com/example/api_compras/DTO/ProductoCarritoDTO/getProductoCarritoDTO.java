package com.example.api_compras.DTO.ProductoCarritoDTO;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class getProductoCarritoDTO {

    //ID's
    private Long id_producto_carrito;
    private Long id_carrito;
    private Long id_historieta;
    
    //Atributos del producto (historieta)
    private String nombre_historieta;
    private Integer cantidad;
    private BigDecimal precio_unitario;

}
