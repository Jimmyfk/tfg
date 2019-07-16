package com.curso.spring.rest.models.dao;

import com.curso.spring.rest.models.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolDao extends JpaRepository<Rol, Long> {
    Rol findByRol(String rol);
}