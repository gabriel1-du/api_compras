package com.example.api_compras.Service;

import java.util.List;

import com.example.api_compras.Model.MedioPago;

public interface MedioPagoService {

    // Metodos GET
    public List<MedioPago> getAllMediosPago(); // Trae todos los medios de pago

    public MedioPago getMedioPagoById(Long id_medio_pago); // Por id
    // --- FIN GET

    // Metodos POST
    public MedioPago saveMedioPago(MedioPago medioPago); // Guardar medio de pago

    // Metodos PUT
    public MedioPago putMedioPago(MedioPago medioPago, Long id_medio_pago); // Actualizar medio de pago

    // Metodos DELETE
    public void deleteMedioPago(Long id_medio_pago); // Eliminar medio de pago
    
}
