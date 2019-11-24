package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ProductoService {

    List<Producto> findByNombreLikeIgnoreCase(String nombre);

    List<Producto> findProductos();

    Producto findById(Integer id);

    Producto findByNombre(String nombre);

    Producto save(Producto producto);

    void delete(Integer id);

    ResponseEntity<?> save(Producto producto, BindingResult result);

    ResponseEntity<?> findAll();

    ResponseEntity<?> find(String nombre);

    ResponseEntity<?> find(Integer id);

    ResponseEntity<?> update(Integer id, Producto producto);

    ResponseEntity<?> remove(Integer id);

    ResponseEntity<?> existenProductos();

    Integer countAll();
}
