package com.example.api_compras.DTO.HistorialComprasDTO;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.api_compras.Model.HistorialCompras;

@Component 
public class HistorialComprasMapper {

    public getHistorialComprasDTO toGetDTO(HistorialCompras historial) {
        if (historial == null) {
            return null;
        }

        getHistorialComprasDTO dto = new getHistorialComprasDTO();
        dto.setId_historial(historial.getId_historial());
        dto.setId_usuario(historial.getId_usuario());

        if (historial.getBoletas() != null) {
            List<getBoletaHIstorialDTO> boletasDTO = historial.getBoletas().stream()
                .map(boleta -> {
                    getBoletaHIstorialDTO bDto = new getBoletaHIstorialDTO();
                    bDto.setId_boleta(boleta.getId_boleta());
                    bDto.setFecha(boleta.getFecha_emision());
                    
                    if (boleta.getMedioPago() != null) {
                        bDto.setMedio_pago(boleta.getMedioPago().getNombre_medio());
                    }
                    
                    return bDto;
                })
                .collect(Collectors.toList());

            dto.setBoletas(boletasDTO);
        }

        return dto;
    }

}
