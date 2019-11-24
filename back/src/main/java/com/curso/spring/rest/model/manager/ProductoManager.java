package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.dao.ProductoDao;
import com.curso.spring.rest.model.entity.Producto;
import com.curso.spring.rest.model.services.ErrorService;
import com.curso.spring.rest.model.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ProductoManager implements ProductoService {

    private final ProductoDao productoDao;
    private final ErrorService errorService;

    @Autowired
    public ProductoManager(ProductoDao productoDao, ErrorService errorService) {
        this.productoDao = productoDao;
        this.errorService = errorService;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombreLikeIgnoreCase(String nombre) {
        return productoDao.findByNombreLikeIgnoreCase('%' + nombre + '%');
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findProductos() {
        return productoDao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Integer id) {
        return productoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findByNombre(String nombre) {
        return productoDao.findByNombreIgnoreCase(nombre);
    }

    @Override
    public Producto save(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        productoDao.deleteById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<?> save(Producto producto, BindingResult result) {
        Producto productoNuevo;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            return this.errorService.throwErrors(result, response);
        }

        try {
            productoNuevo = this.save(producto);
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }
        response.put("mensaje", "Producto guardado");
        response.put("producto", productoNuevo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Producto> productos;
        Map<String, Object> response = new HashMap<>();

        try {
            productos = this.findProductos();
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }
        return ResponseEntity.ok(productos);
    }

    @Override
    public ResponseEntity<?> find(String nombre) {
        Producto producto;
        Map<String, Object> response = new HashMap<>();

        try {
            producto = this.findByNombre(nombre);
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }

        if (producto == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(producto);
    }

    @Override
    public ResponseEntity<?> find(Integer id) {
        Producto producto;
        Map<String, Object> response = new HashMap<>();

        try {
            producto = this.findById(id);
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }

        if (producto == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(producto);
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(Integer id, Producto producto) {
        Producto actual = this.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (actual == null) {
            response.put("error", "El producto no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        try {
            actual.copy(producto);
            this.save(actual);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }
        response.put("producto", actual);
        response.put("mensaje", "Producto actualizado con éxito");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> remove(Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.delete(id);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }
        response.put("mensaje", "Producto eliminado");
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> existenProductos() {
        Map<String, Object> body = new HashMap<>();
        try {
            Integer countProductos = this.countAll();
            body.put("existenProductos", countProductos > 0);
        } catch (Exception e) {
            e.printStackTrace();
            body.put("existenProductos", false);
        }

        return ResponseEntity.ok(body);
    }

    @Override
    public Integer countAll() {
        return productoDao.findAll().size();
    }
}
