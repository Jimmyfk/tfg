package com.curso.spring.rest.controllers;

import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.entity.Factura;
import com.curso.spring.rest.models.services.ClienteService;
import com.curso.spring.rest.models.services.ErrorService;
import com.curso.spring.rest.models.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/facturas")
public class FacturaRestController {

    private final FacturaService facturaService;
    private final ClienteService clienteService;
    private final ErrorService errorService;

    @Autowired
    public FacturaRestController(FacturaService facturaService, ClienteService clienteService, ErrorService errorService) {
        this.facturaService = facturaService;
        this.clienteService = clienteService;
        this.errorService = errorService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        Factura factura;
        Map<String, Object> response = new HashMap<>();

        try {
            factura = facturaService.fetchByIdWithClienteWithItemFacturaWithProducto(id);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
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
            return errorService.dbError(e, response);
        }

        response.put("facturas", facturas);
        return new ResponseEntity<>(facturas, HttpStatus.OK);
    }

    @PostMapping(value = "/{cliente}")
    public ResponseEntity<?> create(@PathVariable Long cliente, @RequestBody Factura factura) {
        Cliente cli;
        Map<String, Object> response = new HashMap<>();

        try {
            cli = clienteService.findById(cliente);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
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

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Factura factura = facturaService.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (factura != null) {
            try {
                facturaService.delete(id);
            } catch (DataAccessException e) {
                return errorService.dbError(e, response);
            }
            response.put("mensaje", "Factura eliminada");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("error", "La factura no existe");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
