package com.example.EjercicioSemana6RM.repository;

import com.example.EjercicioSemana6RM.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}