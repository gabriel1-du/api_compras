package com.example.api_compras.DTO.RestClientDTO;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
public class HistorietaExternoDTO {

    private Long id_historieta;
    private String nombre_historieta;
    private BigDecimal precio;

}
