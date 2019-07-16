package com.curso.spring.rest.models.dao;

import com.curso.spring.rest.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Integer> {

    Usuario findByUsername(String username);

}
