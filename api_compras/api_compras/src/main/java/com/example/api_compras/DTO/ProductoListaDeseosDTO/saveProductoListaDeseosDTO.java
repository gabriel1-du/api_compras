package com.example.api_compras.DTO.ProductoListaDeseosDTO;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data 
@Component 
public class saveProductoListaDeseosDTO {

    private Long id_lista_deseos;
    private Long id_producto;

}
