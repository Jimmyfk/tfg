package com.curso.spring.rest.controllers;

import com.curso.spring.rest.models.entity.Producto;
import com.curso.spring.rest.models.services.ErrorService;
import com.curso.spring.rest.models.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "api/productos")
public class ProductoRestController {

    private final ProductoService productoService;
    private final ErrorService errorService;

    @Autowired
    public ProductoRestController(ProductoService productoService, ErrorService errorService) {
        this.productoService = productoService;
        this.errorService = errorService;
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid Producto producto, BindingResult result) {

        Producto productoNuevo;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            return errorService.throwErrors(result, response);
        }

        try {
            productoNuevo = productoService.save(producto);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }
        response.put("mensaje", "Producto guardado");
        response.put("producto", productoNuevo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping(value = "/search/{nombre}")
    public @ResponseBody
    List<Producto> cargarProducto(@PathVariable String nombre) {
        return productoService.findByNombreLikeIgnoreCase(nombre);
    }

    @GetMapping()
    public ResponseEntity<?> productos() {
        List<Producto> productos;
        Map<String, Object> response = new HashMap<>();

        try {
            productos = productoService.findProductos();
        } catch (DataAccessException e) {
           return errorService.dbError(e, response);
        }
        response.put("productos", productos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/find/{nombre}")
    public ResponseEntity<?> find(@PathVariable String nombre) {
        Producto producto;
        Map<String, Object> response = new HashMap();

        try {
            producto = productoService.findByNombre(nombre);
        } catch (DataAccessException e) {
           return errorService.dbError(e, response);
        }

        if (producto == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Producto producto;
        Map<String, Object> response = new HashMap();

        try {
            producto = productoService.findById(id);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }

        if (producto == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Producto producto) {
        Producto actual = productoService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (actual == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            actual.copy(producto);
            productoService.save(actual);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }
        response.put("producto", actual);
        response.put("mensaje", "Producto actualizado con éxito");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            productoService.delete(id);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }
        response.put("mensaje", "Producto eliminado");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
