package com.example.api_compras.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api_compras.Model.HistorialCompras;

public interface HistorialComprasRepository extends JpaRepository<HistorialCompras , Long> {

    @Query("SELECT h FROM HistorialCompras h WHERE h.id_usuario = :id_usuario")
    List<HistorialCompras> findByUsuario(@Param("id_usuario") Long id_usuario);

    @Query("SELECT COUNT(h) > 0 FROM HistorialCompras h WHERE h.id_usuario = :id_usuario")
    boolean existsByUsuario(@Param("id_usuario") Long id_usuario);

}
