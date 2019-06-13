package com.curso.spring.rest.controllers;

import com.curso.spring.rest.models.dao.FacturaDao;
import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.entity.Factura;
import com.curso.spring.rest.models.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class FacturaRestController {

    private final FacturaDao facturaDao;
    private final ClienteService clienteService;

    @Autowired
    public FacturaRestController(FacturaDao facturaDao, ClienteService clienteService) {
        this.facturaDao = facturaDao;
        this.clienteService = clienteService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Factura factura;
        Map<String, Object> response = new HashMap<>();

        try {
            factura = facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
        } catch(DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (factura == null) {
            response.put("mensaje", "La factura nº " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(factura, HttpStatus.OK);
    }

    @PostMapping(value = "/facturas/nueva/{cliente}")
    public ResponseEntity<?> create(@PathVariable Long cliente) {
        Cliente cli;
        Map<String, Object> response = new HashMap<>();

        try {
            cli = clienteService.findById(cliente);
        } catch(DataAccessException e) {
            response.put("error", "Error al consultar la base de datos ");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cli == null) {
            response.put("error", "El cliente " + cliente + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Factura factura = new Factura(cli);
        response.put("factura", factura);
        response.put("mensaje", "Factura creada correctamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
