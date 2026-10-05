package com.example.api_compras.DTO.ProductoListaDeseosDTO;


import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component 
@Data 
public class getProductoListaDeseosDTO {

    private Long id_prod_list_deseos;
    private Long id_lista_desoes;
    private Long id_producto;
    private String nombre_producto;
    private LocalDateTime fecha_agregado;
}
