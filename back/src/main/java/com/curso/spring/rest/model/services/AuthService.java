package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.entity.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    Usuario findByUsername(String username);

    Rol findByRol(String rol);

    Privilegio findByPrivilegio(String privilegio);

    Usuario saveUsuario(Usuario usuario);

    Rol saveRol(Rol rol);

    Privilegio savePrivilegio(Privilegio privilegio);

    long countUsuarios();



}
