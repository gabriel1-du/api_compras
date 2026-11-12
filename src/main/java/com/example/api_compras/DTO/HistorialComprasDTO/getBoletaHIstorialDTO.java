package com.example.api_compras.DTO.HistorialComprasDTO;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data 
@Component 
public class getBoletaHIstorialDTO {

    private Long id_boleta;
    private String medio_pago;
    private LocalDateTime fecha;

}
