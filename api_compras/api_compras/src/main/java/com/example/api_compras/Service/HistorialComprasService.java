package com.example.api_compras.Service;

import java.util.List;

import com.example.api_compras.DTO.HistorialComprasDTO.getHistorialComprasDTO;
import com.example.api_compras.DTO.HistorialComprasDTO.saveHistorialComprasDTO;

public interface HistorialComprasService {

    // Metodos GET
    List<getHistorialComprasDTO> getAllHistorialCompras();

    getHistorialComprasDTO getHistorialComprasById(Long id_historial);

    List<getHistorialComprasDTO> getHistorialComprasByUsuario(Long id_usuario);
    // --- FIN GET

    // Metodo POST
    getHistorialComprasDTO saveHistorialCompras(saveHistorialComprasDTO dto);

}
