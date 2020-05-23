package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.entity.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticación, es el que será usado en los controladores
 */
@Service
public interface AuthService {

    /**
     *
     * @see com.curso.spring.rest.model.dao.UsuarioDao#findByUsername(String)
     */
    Usuario findByUsername(String username);

    Usuario findByClienteId(Integer clienteId);

    /**
     *
     * @see com.curso.spring.rest.model.dao.RolDao#findByRol(String)
     */
    Rol findByRol(String rol);

    /**
     * @see com.curso.spring.rest.model.dao.PrivilegioDao#findByPrivilegio(String)
     */
    Privilegio findByPrivilegio(String privilegio);

    /**
     * Guarda un usuario y lo retorna, ya que el guardado ha podido modificar la instancia.
     *
     * @param usuario usuario a guardar, no puede ser {@literal null}
     * @return el usuario guardado
     */
    Usuario saveUsuario(Usuario usuario);


    /**
     * Modifica la contraseña de un usuario y añade el resultado a la respuesta
     *
     * @param usuarioId id del usuario que se va a modificar
     * @param password nueva contraseña del usuario
     * @return respuesta con el resultado de la operación
     */
    ResponseEntity<?> actualizarPassword(Integer usuarioId, String password);

    /**
     * Crea un usuario para un cliente
     * @param usuario usuario a guardar
     * @return el usuario guardado
     */
    Usuario saveCliente(Usuario usuario);

    /** ELimina un usuario
     *
     * @param usuario usuario a eliminar
     */
    void deleteUsuario(Usuario usuario);

    /**
     * Guarda un rol y lo retorna
     *
     * @param rol el rol a guardar
     * @return el rol guardado
     */
    Rol saveRol(Rol rol);

    /**
     * Guarda un privilegio y lo retorna
     *
     * @param privilegio el privilegio a guardar
     * @return el privilegio guardado
     */
    Privilegio savePrivilegio(Privilegio privilegio);

    /**
     * Cuenta el número de usuarios
     *
     * @return el número de usuarios
     */
    long countUsuarios();

    /**
     * Cuenta el número de roles
     *
     * @return el número de roles
     */
    long countRoles();
}
