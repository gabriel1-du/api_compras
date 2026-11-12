package com.example.api_compras.DTO.BoletaDTO;


import org.springframework.stereotype.Component;

import lombok.Data;

@Data 
@Component 
public class saveBoletaDTO {

    private Long id_usuario;
    private Long id_historial;
    private Long id_medio_pago;
    private Long id_carrito;
    private String rut_comprador;
    private String nombre_comprador;


}
