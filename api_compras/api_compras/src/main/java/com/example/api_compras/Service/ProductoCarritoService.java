package com.example.api_compras.Service;

import java.util.List;

import com.example.api_compras.DTO.ProductoCarritoDTO.getProductoCarritoDTO;
import com.example.api_compras.DTO.ProductoCarritoDTO.putCarritoDTO;
import com.example.api_compras.DTO.ProductoCarritoDTO.saveCarritoProductoCartDTO;

public interface ProductoCarritoService {

    //metodos get
    public getProductoCarritoDTO getProductoCarritoByid(Long id_producto_carrito);

    public List<getProductoCarritoDTO> getAllProductoCarrito();
    //fin metodos post
    public getProductoCarritoDTO saveProductoCarrito(saveCarritoProductoCartDTO dto);

    public getProductoCarritoDTO putProductoCarrito(Long id_producto_carrito, putCarritoDTO putDto);
};
