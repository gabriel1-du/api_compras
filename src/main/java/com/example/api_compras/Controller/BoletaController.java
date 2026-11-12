package com.example.api_compras.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api_compras.DTO.BoletaDTO.getBoletaDTO;
import com.example.api_compras.DTO.BoletaDTO.saveBoletaDTO;
import com.example.api_compras.Service.BoletaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/boletasApi")
@RequiredArgsConstructor
public class BoletaController {

    private final BoletaService boletaService;

    @GetMapping("/")
    public ResponseEntity<List<getBoletaDTO>> getAllBoletas() {
        return ResponseEntity.ok(boletaService.getAllBoletas());
    }

    @GetMapping("/{id_boleta}")
    public ResponseEntity<?> getBoletaById(@PathVariable("id_boleta") Long id_boleta) {
        try {
            return ResponseEntity.ok(boletaService.getBoletaById(id_boleta));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> saveBoleta(@RequestBody saveBoletaDTO dto) {
        try {
            getBoletaDTO nuevaBoleta = boletaService.saveBoleta(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaBoleta);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



    @DeleteMapping("/{id_boleta}")
    public ResponseEntity<?> deleteBoleta(@PathVariable("id_boleta") Long id_boleta) {
        try {
            boletaService.deleteBoleta(id_boleta);
            return ResponseEntity.ok("Boleta eliminada exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
