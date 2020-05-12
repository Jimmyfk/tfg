package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.entity.Usuario;
import org.springframework.stereotype.Service;

/**
 * Servicio de autenticaci�n, es el que ser� usado en los controladores
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
     * Cuenta el n�mero de usuarios
     *
     * @return el n�mero de usuarios
     */
    long countUsuarios();

    /**
     * Cuenta el n�mero de roles
     *
     * @return el n�mero de roles
     */
    long countRoles();
}
