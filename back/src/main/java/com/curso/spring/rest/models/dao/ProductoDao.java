package com.curso.spring.rest.models.dao;

import com.curso.spring.rest.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoDao extends JpaRepository<Producto, Long> {

    List<Producto> findByNombreLikeIgnoreCase(String nombre);
}
