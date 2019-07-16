package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolDao extends JpaRepository<Rol, Integer> {
    Rol findByRol(String rol);
}
