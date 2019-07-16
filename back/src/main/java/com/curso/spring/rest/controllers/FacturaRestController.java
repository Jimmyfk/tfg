package com.curso.spring.rest.controllers;

import com.curso.spring.rest.models.entity.Factura;
import com.curso.spring.rest.models.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/facturas")
public class FacturaRestController {

    private final FacturaService facturaService;

    @Autowired
    public FacturaRestController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
        return this.facturaService.show(id);
    }

    @GetMapping(value = "/ver/{id}")
    public ResponseEntity<?> list(@PathVariable Integer id) {
        return this.facturaService.getFacturasCliente(id);
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @PostMapping(value = "/{cliente}")
    public ResponseEntity<?> create(@PathVariable Integer cliente, @RequestBody Factura factura) {
        return this.facturaService.create(cliente, factura);
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return this.facturaService.remove(id);
    }

}
