package com.curso.spring.rest.controllers;

import com.curso.spring.rest.models.dao.FacturaDao;
import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.entity.Factura;
import com.curso.spring.rest.models.entity.Producto;
import com.curso.spring.rest.models.services.ClienteService;
import com.curso.spring.rest.models.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/facturas")
public class FacturaRestController {

    private final FacturaDao facturaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    @Autowired
    public FacturaRestController(FacturaDao facturaService, ClienteService clienteService, ProductoService productoService) {
        this.facturaService = facturaService;
        this.clienteService = clienteService;
        this.productoService = productoService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Factura factura;
        Map<String, Object> response = new HashMap<>();

        try {
            factura = facturaService.fetchByIdWithClienteWithItemFacturaWithProducto(id);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (factura == null) {
            response.put("mensaje", "La factura " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(factura, HttpStatus.OK);
    }

    @GetMapping(value = "/ver/{id}")
    public ResponseEntity<?> list(@PathVariable Long id) {
        List<Factura> facturas;
        Map<String, Object> response = new HashMap<>();

        try {
            facturas = facturaService.findAllByCliente(clienteService.findById(id));
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("facturas", facturas);
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    @PostMapping(value = "/nueva/{cliente}")
    public ResponseEntity<?> create(@PathVariable Long cliente, @RequestBody Factura factura) {
        Cliente cli;
        Map<String, Object> response = new HashMap<>();

        try {
            cli = clienteService.findById(cliente);
        } catch (DataAccessException e) {
            response.put("error", "Error al consultar la base de datos ");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cli == null) {
            response.put("error", "El cliente " + cliente + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        facturaService.save(factura);
        response.put("factura", factura);
        response.put("mensaje", "Factura creada correctamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
