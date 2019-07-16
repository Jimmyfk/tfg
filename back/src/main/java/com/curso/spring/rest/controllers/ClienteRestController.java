package com.curso.spring.rest.controllers;

import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.services.ClienteService;
import com.curso.spring.rest.models.services.ErrorService;
import export.ClienteList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

    private final ClienteService clienteService;

    @Autowired
    public ClienteRestController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // todo: Adaptar el front a la paginación
    @GetMapping()
    public ClienteList index(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                               @RequestParam(defaultValue = "1000", required = false) int size) {
        return this.clienteService.index(page, size);
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ClienteList export() {
        return this.clienteService.export();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Integer id) {
       return this.clienteService.show(id);
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result) {
        return this.clienteService.create(cliente, result);
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Integer id) {
        return this.clienteService.update(cliente, result, id);
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return this.clienteService.remove(id);
    }

}
