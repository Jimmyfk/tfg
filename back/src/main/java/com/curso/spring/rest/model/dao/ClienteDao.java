package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Dao para {@link Cliente}</p>
 * Permite hacer operaciones en bases de datos con clientes, no tiene métodos por que nos vale con los que hereda de
 * {@link JpaRepository}
 */
@Repository
public interface ClienteDao extends JpaRepository<Cliente, Integer> {

}
