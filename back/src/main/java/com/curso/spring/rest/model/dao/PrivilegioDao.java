package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Privilegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegioDao extends JpaRepository<Privilegio, Integer> {

    Privilegio findByPrivilegio(String privilegio);
}
