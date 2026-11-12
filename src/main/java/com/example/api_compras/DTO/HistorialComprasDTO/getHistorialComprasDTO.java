package com.example.api_compras.DTO.HistorialComprasDTO;



import java.util.List;

import lombok.Data;

@Data 
public class getHistorialComprasDTO {

    private Long id_historial;
    private Long id_usuario;
    
    // Arreglo de boletas asociadas a este historial
    private List<getBoletaHIstorialDTO> boletas;
    
}
