package com.curso.spring.rest.models.dao;

import com.curso.spring.rest.models.entity.Privilegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegioDao extends JpaRepository<Privilegio, Long> {

    Privilegio findByPrivilegio(String privilegio);
}
