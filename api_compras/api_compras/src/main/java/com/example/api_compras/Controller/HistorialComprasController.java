package com.example.api_compras.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_compras.DTO.HistorialComprasDTO.getHistorialComprasDTO;
import com.example.api_compras.DTO.HistorialComprasDTO.saveHistorialComprasDTO;
import com.example.api_compras.Service.HistorialComprasService;

@RestController
@RequestMapping("/api/historialCompras")
public class HistorialComprasController {

    @Autowired
    private HistorialComprasService historialService;

    // metodos get
    @GetMapping("/")
    public ResponseEntity<List<getHistorialComprasDTO>> getAllHistorialCompras() {
        return ResponseEntity.ok(historialService.getAllHistorialCompras());
    }

    @GetMapping("/{id_historial}")
    public ResponseEntity<?> getHistorialComprasById(@PathVariable("id_historial") Long id_historial) {
        try {
            return ResponseEntity.ok(historialService.getHistorialComprasById(id_historial));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/usuario/{id_usuario}")
    public ResponseEntity<List<getHistorialComprasDTO>> getHistorialComprasByUsuario(
            @PathVariable("id_usuario") Long id_usuario) {
        return ResponseEntity.ok(historialService.getHistorialComprasByUsuario(id_usuario));
    }
    // ----- fin metodos get

    // metodo POST
    @PostMapping("/")
    public ResponseEntity<?> saveHistorialCompras(@RequestBody saveHistorialComprasDTO dto) {
        try {
            getHistorialComprasDTO nuevo = historialService.saveHistorialCompras(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
