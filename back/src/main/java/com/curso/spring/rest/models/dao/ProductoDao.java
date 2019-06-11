package com.curso.spring.rest.models.dao;

import com.curso.spring.rest.models.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoDao extends JpaRepository<Producto, Long> {

    @Query("select p from Producto p where p.nombre like %?1%")
    List<Producto> findByNombre(String nombre);

    List<Producto> findByNombreLikeIgnoreCase(String nombre);
}
