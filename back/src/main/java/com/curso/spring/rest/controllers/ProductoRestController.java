package com.curso.spring.rest.controllers;

import com.curso.spring.rest.models.entity.Producto;
import com.curso.spring.rest.models.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "api/productos")
public class ProductoRestController {

    private ProductoService productoService;

    @Autowired
    public ProductoRestController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping(value = "/search/{nombre}")
    public @ResponseBody
    List<Producto> cargarProducto(@PathVariable String nombre) {
        return productoService.findByNombreLikeIgnoreCase(nombre);
    }

    @GetMapping(value = "/cargar")
    public ResponseEntity<?> productos() {
        List<Producto> productos;
        Map<String, Object> response = new HashMap<>();

        try {
            productos = productoService.findProductos();
        } catch (DataAccessException e) {
            response.put("error", "Error al consultar la base de datos ");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
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
            response.put("error", "Error al consultar la base de datos");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/id/{id}")
    public ResponseEntity<?> find(@PathVariable Long id) {
        Producto producto;
        Map<String, Object> response = new HashMap();

        try {
            producto = productoService.findById(id);
        } catch (DataAccessException e) {
            response.put("error", "Error al consultar la base de datos");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (producto == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        response.put("producto", producto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
