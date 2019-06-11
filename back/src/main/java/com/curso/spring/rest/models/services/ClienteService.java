package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable);

    Cliente save(Cliente cliente);

    Cliente findById(Long id);

    void delete(Long id);

    void delete(Cliente cliente);

    boolean exists(Long id);
}
