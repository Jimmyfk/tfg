package com.curso.spring.rest.models.dao;

import com.curso.spring.rest.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDao extends JpaRepository<Cliente, Long> {

}
