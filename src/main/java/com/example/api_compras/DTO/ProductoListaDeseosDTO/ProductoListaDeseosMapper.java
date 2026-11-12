package com.example.api_compras.DTO.ProductoListaDeseosDTO;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.api_compras.DTO.RestClientDTO.HistorietaExternoDTO;
import com.example.api_compras.Model.ListaDeseos;
import com.example.api_compras.Model.ProductoListaDeseos;
import com.example.api_compras.Repository.ListaDeseosRepository;
import com.example.api_compras.RestClient.HistorietaClient;


import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class ProductoListaDeseosMapper {

    @Autowired 
    private final ListaDeseosRepository listaDeseosRepo;

    @Autowired 
    private final HistorietaClient histClient;


    public getProductoListaDeseosDTO toGetDTO(ProductoListaDeseos prodLista) {
        if (prodLista == null) {
            return null;
        }

        getProductoListaDeseosDTO dto = new getProductoListaDeseosDTO();
        
        dto.setId_prod_list_deseos(prodLista.getId_prod_lista_deseos());
        
        if (prodLista.getListaDeseos() != null) {
            dto.setId_lista_desoes(prodLista.getListaDeseos().getId_lista_deseos());
        }

        dto.setId_producto(prodLista.getId_producto());
        dto.setFecha_agregado(prodLista.getFecha_agregado());

        // Consultar al microservicio externo la información del producto (historieta)
        HistorietaExternoDTO histoExtern = histClient.obtenerHistorietaPorId(prodLista.getId_producto());
        if (histoExtern != null) {
            dto.setNombre_producto(histoExtern.getNombre_historieta());
        } else {
            dto.setNombre_producto("Producto no disponible");
        }

        return dto;
    }

    
    public ProductoListaDeseos toEntityFromSaveDTO(saveProductoListaDeseosDTO dto) {

        if (dto == null) {
            return null;
        }

        // Validar que la Lista de Deseos exista en la base de datos local
        ListaDeseos listaDeseosExistente = listaDeseosRepo.findById(dto.getId_lista_deseos())
                .orElseThrow(() -> new RuntimeException("ID de lista de deseos inexistente: " + dto.getId_lista_deseos()));

        // Validar que la Historieta/Producto exista en el microservicio externo
        HistorietaExternoDTO histoExtern = histClient.obtenerHistorietaPorId(dto.getId_producto());
        if (histoExtern == null) {
            throw new RuntimeException("ID de producto (historieta) inexistente: " + dto.getId_producto());
        }

        ProductoListaDeseos prodLista = new ProductoListaDeseos();
        
        prodLista.setListaDeseos(listaDeseosExistente);
        prodLista.setId_producto(dto.getId_producto());
        
        // Asignar automáticamente la fecha y hora actual en el momento de agregar
        prodLista.setFecha_agregado(LocalDateTime.now());

        return prodLista;
    }

   
   
}
