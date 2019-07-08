package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.entity.Privilegio;
import com.curso.spring.rest.models.entity.Rol;
import com.curso.spring.rest.models.entity.Usuario;
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
