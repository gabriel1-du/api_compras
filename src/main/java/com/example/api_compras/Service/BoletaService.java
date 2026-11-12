package com.example.api_compras.Service;

import java.util.List;

import com.example.api_compras.DTO.BoletaDTO.getBoletaDTO;
import com.example.api_compras.DTO.BoletaDTO.saveBoletaDTO;

public interface BoletaService {

    List<getBoletaDTO> getAllBoletas();
    getBoletaDTO getBoletaById(Long id_boleta);
    getBoletaDTO saveBoleta(saveBoletaDTO dto);
    //getBoletaDTO putBoleta(Long id_boleta, putBoletaDTO dto);
    void deleteBoleta(Long id_boleta);

}
