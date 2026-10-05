package com.example.api_compras.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.api_compras.DTO.ProductoListaDeseosDTO.ProductoListaDeseosMapper;
import com.example.api_compras.DTO.ProductoListaDeseosDTO.getProductoListaDeseosDTO;
import com.example.api_compras.DTO.ProductoListaDeseosDTO.saveProductoListaDeseosDTO;
import com.example.api_compras.Model.ProductoListaDeseos;
import com.example.api_compras.Repository.ProductoListaDeseosRepository;
import com.example.api_compras.Service.ProductoListaDeseosService;


import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor 
public class ProductoListaDeseosServiceImpl  implements ProductoListaDeseosService {


    @Autowired 
    private final ProductoListaDeseosRepository repo;

    @Autowired
    private final ProductoListaDeseosMapper mapper;

    // Métodos Get
    public List<getProductoListaDeseosDTO> getAllProductoListaDeseos() {
        return repo.findAll()
                .stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    public getProductoListaDeseosDTO getProductoListaDeseosById(Long id_prod_lista_deseos) {
        ProductoListaDeseos productoLista = repo.findById(id_prod_lista_deseos)
                .orElseThrow(() -> new RuntimeException("Producto en lista de deseos no encontrado con el ID: " + id_prod_lista_deseos));

        return mapper.toGetDTO(productoLista);
    }

    // Fin Métodos Get
    public getProductoListaDeseosDTO saveProductoListaDeseos(saveProductoListaDeseosDTO dto) {
        try {
            // El mapper se encarga de validar la existencia de la lista de deseos local y la historieta externa
            ProductoListaDeseos productoNuevo = mapper.toEntityFromSaveDTO(dto);

            ProductoListaDeseos productoGuardado = repo.save(productoNuevo);

            return mapper.toGetDTO(productoGuardado);

        } catch (Exception e) {
            throw new RuntimeException("Error al guardar el producto en la lista de deseos: " + e.getMessage());
        }
    }

    public void deleteProductoListaDeseos(Long id_prod_lista_deseos) {
        ProductoListaDeseos producto_del = repo.findById(id_prod_lista_deseos)
                .orElseThrow(() -> new RuntimeException("Producto en lista de deseos no encontrado con el ID para eliminar: " + id_prod_lista_deseos));

        repo.delete(producto_del);
    }

}
