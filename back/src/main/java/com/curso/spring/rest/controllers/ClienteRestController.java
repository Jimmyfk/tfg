package com.curso.spring.rest.controllers;

import com.curso.spring.rest.model.entity.Cliente;
import com.curso.spring.rest.model.services.AuthService;
import com.curso.spring.rest.model.services.ClienteService;
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
    private final AuthService authService;

    @Autowired
    public ClienteRestController(ClienteService clienteService, AuthService authService) {
        this.clienteService = clienteService;
        this.authService = authService;
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

    @PostMapping("/{id}/modificar-password")
    public ResponseEntity<?> updatePassword(@PathVariable Integer id, @RequestParam String password) {
        return this.authService.actualizarPassword(id, password);
    }

    @GetMapping("/existen")
    public ResponseEntity<?> existenClientes() {
        return this.clienteService.existenClientes();
    }

    @PreAuthorize("hasAuthority('WRITE_PRIVILEGE')")
    @PostMapping()
    public ResponseEntity<?> create(@Valid @RequestBody Cliente cliente, BindingResult result, @RequestParam String password) {
        return this.clienteService.create(cliente, result, password);
    }

    @GetMapping("/usuario/{clienteId}")
    public ResponseEntity<?> getUsuario(@PathVariable Integer clienteId) {
        return this.clienteService.getUsuario(clienteId);
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
