package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteDao extends JpaRepository<Cliente, Integer> {

}
