package com.example.api_compras.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.api_compras.DTO.ProductoCarritoDTO.getProductoCarritoDTO;
import com.example.api_compras.DTO.ProductoCarritoDTO.saveCarritoProductoCartDTO;
import com.example.api_compras.Service.ProductoCarritoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController 
@RequestMapping("/api/ProductoCartRequest")
public class ProductoCarritoController {

    @Autowired 
    private ProductoCarritoService prodServ;


    //metodos get
    @GetMapping("/{id_producto_carrito}")
    public ResponseEntity<?> getCarritoById(@PathVariable("id_producto_carrito") Long id_prod){
        try{

            getProductoCarritoDTO prod = prodServ.getProductoCarritoByid(id_prod);
            return ResponseEntity.ok(prod);
        } catch(RuntimeException e){
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    } 

    //metodo post 
    @PostMapping("/")
    public ResponseEntity<?> saveProductoCarrito(@RequestBody saveCarritoProductoCartDTO prod){
        
        try{
            getProductoCarritoDTO prod_nuevo =  prodServ.saveProductoCarrito(prod);
            return ResponseEntity.ok(prod_nuevo);

        } catch (RuntimeException e){

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
            
    }



}
