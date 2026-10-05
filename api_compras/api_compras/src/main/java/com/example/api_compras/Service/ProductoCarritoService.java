package com.example.api_compras.Service;

import com.example.api_compras.DTO.ProductoCarritoDTO.getProductoCarritoDTO;
import com.example.api_compras.DTO.ProductoCarritoDTO.saveCarritoProductoCartDTO;
import com.example.api_compras.Model.ProductoCarrito;

public interface ProductoCarritoService {

    //metodos get
    public getProductoCarritoDTO getProductoCarritoByid(Long id_producto_carrito);


    public getProductoCarritoDTO saveProductoCarrito(saveCarritoProductoCartDTO dto); 
};
