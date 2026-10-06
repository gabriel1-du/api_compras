package com.example.api_compras.DTO.BoletaDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data 
@Component 
public class getBoletaDTO {

    private Long id_boleta;
    private String nombre;     
    private String medio_pago;         
    
    private List<DetalleProductoDTO> producto; 
    
    private BigDecimal total;          
    private LocalDateTime fecha;        // fecha_emision

    // Sub-DTO interno para listar los productos detallados
    @Data
    public static class DetalleProductoDTO {
        private String nombre_producto;
        private Integer cantidad;
        private BigDecimal precio_unitario;
    }

}
