package com.example.api_compras.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_compras.DTO.ProductoListaDeseosDTO.getProductoListaDeseosDTO;
import com.example.api_compras.DTO.ProductoListaDeseosDTO.saveProductoListaDeseosDTO;
import com.example.api_compras.Service.ProductoListaDeseosService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/productoListaDeseosApi")
@AllArgsConstructor
public class ProductoListaDeseosController {

    @Autowired
    private final ProductoListaDeseosService productoListaDeseosService;

    // Métodos Get
    @GetMapping("/")
    public ResponseEntity<List<getProductoListaDeseosDTO>> getAllProductoListaDeseos() {
        List<getProductoListaDeseosDTO> lista = productoListaDeseosService.getAllProductoListaDeseos();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id_prod_lista_deseos}")
    public ResponseEntity<?> getProductoListaDeseosById(@PathVariable("id_prod_lista_deseos") Long id_prod_lista_deseos) {
        try {
            getProductoListaDeseosDTO producto = productoListaDeseosService.getProductoListaDeseosById(id_prod_lista_deseos);
            return ResponseEntity.ok(producto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> saveProductoListaDeseos(@RequestBody saveProductoListaDeseosDTO dto) {
        try {
            getProductoListaDeseosDTO nuevoProducto = productoListaDeseosService.saveProductoListaDeseos(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id_prod_lista_deseos}")
    public ResponseEntity<?> deleteProductoListaDeseos(@PathVariable("id_prod_lista_deseos") Long id_prod_lista_deseos) {
        try {
            productoListaDeseosService.deleteProductoListaDeseos(id_prod_lista_deseos);
            return ResponseEntity.ok("Producto en lista de deseos eliminado exitosamente");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
