package com.example.api_compras.Model;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "HISTORIAL_COMPRAS")
public class HistorialCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_historial;

    // Referencia lógica a ms-usuarios
    @Column(name = "id_usuario", nullable = false)
    private Long id_usuario;

    // Relación bidireccional física
    @OneToMany(mappedBy = "historial", cascade = CascadeType.ALL)
    private List<Boleta> boletas;

}
