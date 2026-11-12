package com.example.api_compras.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.api_compras.Model.ProductoCarrito;

public interface ProductoCarritoRepository extends JpaRepository<ProductoCarrito, Long>{
     @Query("SELECT p FROM ProductoCarrito p WHERE p.carrito.id_carrito = :idCarrito")
    List<ProductoCarrito> findByCarritoId(@Param("idCarrito") Long idCarrito);
}
