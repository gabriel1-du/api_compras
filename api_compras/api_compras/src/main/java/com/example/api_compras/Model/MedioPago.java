package com.example.api_compras.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "MEDIOS_PAGO")
public class MedioPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_medio_pago;

    @Column(name = "nombre_medio", nullable = false)
    private String nombre_medio;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

}
