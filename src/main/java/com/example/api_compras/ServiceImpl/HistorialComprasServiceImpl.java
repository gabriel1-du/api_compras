package com.example.api_compras.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.api_compras.DTO.HistorialComprasDTO.HistorialComprasMapper;
import com.example.api_compras.DTO.HistorialComprasDTO.getHistorialComprasDTO;
import com.example.api_compras.DTO.HistorialComprasDTO.saveHistorialComprasDTO;
import com.example.api_compras.DTO.RestClientDTO.UsuarioExternoDTO;
import com.example.api_compras.Model.HistorialCompras;
import com.example.api_compras.Repository.HistorialComprasRepository;
import com.example.api_compras.RestClient.UsuarioClient;
import com.example.api_compras.Service.HistorialComprasService;

@Service
public class HistorialComprasServiceImpl implements HistorialComprasService {

    @Autowired
    private HistorialComprasRepository historialRepo;

    @Autowired
    private UsuarioClient usuarioClient;

    @Autowired
    private HistorialComprasMapper mapper;

    // Metodos GET
    @Override
    @Transactional(readOnly = true)
    public List<getHistorialComprasDTO> getAllHistorialCompras() {
        return historialRepo.findAll().stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public getHistorialComprasDTO getHistorialComprasById(Long id_historial) {
        HistorialCompras historial = historialRepo.findById(id_historial)
                .orElseThrow(() -> new RuntimeException("Historial de compras no encontrado con id: " + id_historial));

        return mapper.toGetDTO(historial);
    }

    @Override
    @Transactional(readOnly = true)
    public List<getHistorialComprasDTO> getHistorialComprasByUsuario(Long id_usuario) {
        return historialRepo.findByUsuario(id_usuario).stream()
                .map(mapper::toGetDTO)
                .collect(Collectors.toList());
    }
    // --- FIN GET

    // Metodo POST
    // No requiere una compra (boleta) previa: se crea vacio y las boletas se asocian despues.
    @Override
    @Transactional
    public getHistorialComprasDTO saveHistorialCompras(saveHistorialComprasDTO dto) {
        if (dto == null || dto.getId_usuario() == null) {
            throw new RuntimeException("El id_usuario es obligatorio para crear un historial de compras.");
        }

        // Validar que el usuario exista en ms-usuarios
        UsuarioExternoDTO usuario = usuarioClient.getUsuarioById(dto.getId_usuario());
        if (usuario == null) {
            throw new RuntimeException("Usuario no encontrado con id: " + dto.getId_usuario());
        }

        // Un historial por usuario
        if (historialRepo.existsByUsuario(dto.getId_usuario())) {
            throw new RuntimeException("El usuario " + dto.getId_usuario() + " ya tiene un historial de compras.");
        }

        HistorialCompras historial = new HistorialCompras();
        historial.setId_usuario(dto.getId_usuario());

        HistorialCompras guardado = historialRepo.save(historial);

        return mapper.toGetDTO(guardado);
    }

}
