package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Cliente;
import export.ClienteList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable);

    Cliente save(Cliente cliente);

    Cliente findById(Integer id);

    boolean exists(Integer id);

    ClienteList index(int page, int size);

    ClienteList export();

    ResponseEntity<?> show(Integer id);

    ResponseEntity<?> create(Cliente cliente, BindingResult result);

    ResponseEntity<?> update(Cliente cliente, BindingResult result, Integer id);

    ResponseEntity<?> remove(Integer id);

    ResponseEntity<?> existenClientes();

    Integer countAll();
}
