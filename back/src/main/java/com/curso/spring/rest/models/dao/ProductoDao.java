package com.curso.spring.rest.models.dao;

import com.curso.spring.rest.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoDao extends JpaRepository<Producto, Integer> {

    List<Producto> findByNombreLikeIgnoreCase(String nombre);

    Producto findByNombreIgnoreCase(String nombre);
}
