package com.curso.spring.rest.controllers;

import com.curso.spring.rest.model.entity.Producto;
import com.curso.spring.rest.model.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/productos")
public class ProductoRestController {

    private final ProductoService productoService;

    @Autowired
    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Producto producto, BindingResult result) {
        return this.productoService.save(producto, result);
    }

    @GetMapping(value = "/search/{nombre}")
    public @ResponseBody
    List<Producto> cargarProducto(@PathVariable String nombre) {
        return productoService.findByNombreLikeIgnoreCase(nombre);
    }

    @GetMapping()
    public ResponseEntity<?> findAll() {
        return this.productoService.findAll();
    }

    @GetMapping(value = "/find/{nombre}")
    public ResponseEntity<?> find(@PathVariable String nombre) {
        return this.productoService.find(nombre);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> find(@PathVariable Integer id) {
        return this.productoService.find(id);
    }

    @GetMapping()
    public ResponseEntity<?> existenProductos() {
        return productoService.existenProductos();
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Producto producto) {
        return this.productoService.update(id, producto);
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return this.productoService.remove(id);
    }
}
