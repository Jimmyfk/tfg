package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.entity.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> findByNombreLikeIgnoreCase(String nombre);
}
