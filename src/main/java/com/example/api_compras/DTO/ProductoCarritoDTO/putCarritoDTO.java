package com.example.api_compras.DTO.ProductoCarritoDTO;

import java.math.BigDecimal;

import lombok.Data;

@Data 
public class putCarritoDTO {

    private Integer cantidad;
    private BigDecimal precio_unitario;

}
