package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Integer> {

    Usuario findByUsername(String username);

}
