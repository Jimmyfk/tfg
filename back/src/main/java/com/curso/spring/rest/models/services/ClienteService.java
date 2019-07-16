package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.entity.Cliente;
import export.ClienteList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable);

    Cliente save(Cliente cliente);

    Cliente findById(Long id);

    void delete(Long id);

    void delete(Cliente cliente);

    boolean exists(Long id);

    ClienteList index(int page, int size);

    ClienteList export();

    ResponseEntity<?> show(Long id);

    ResponseEntity<?> create(Cliente cliente, BindingResult result);

    ResponseEntity<?> update(Cliente cliente, BindingResult result, Long id);

    ResponseEntity<?> remove(Long id);
}
