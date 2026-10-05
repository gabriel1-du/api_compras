package com.example.api_compras.Service;

import java.util.List;

import com.example.api_compras.DTO.ProductoListaDeseosDTO.getProductoListaDeseosDTO;
import com.example.api_compras.DTO.ProductoListaDeseosDTO.saveProductoListaDeseosDTO;

public interface ProductoListaDeseosService {

    
    public List<getProductoListaDeseosDTO> getAllProductoListaDeseos();

    public getProductoListaDeseosDTO getProductoListaDeseosById(Long id_prod_lista_deseos);
    

    public getProductoListaDeseosDTO saveProductoListaDeseos(saveProductoListaDeseosDTO dto);

    public void deleteProductoListaDeseos(Long id_prod_lista_deseos);

}
