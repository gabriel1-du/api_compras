package com.example.api_compras.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api_compras.Model.Boleta;
import com.example.api_compras.Model.ProductoCarrito;

public interface BoletaRepository extends JpaRepository<Boleta, Long>{


}
