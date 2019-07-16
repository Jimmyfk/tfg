package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.entity.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ProductoService {

    List<Producto> findByNombreLikeIgnoreCase(String nombre);

    List<Producto> findProductos();

    Producto findById(Long id);

    Producto findByNombre(String nombre);

    Producto save(Producto producto);

    void delete(Long id);

    ResponseEntity<?> save(Producto producto, BindingResult result);

    ResponseEntity<?> findAll();

    ResponseEntity<?> find(String nombre);

    ResponseEntity<?> find(Long id);

    ResponseEntity<?> update(Long id, Producto producto);

    ResponseEntity<?> remove(Long id);
}
