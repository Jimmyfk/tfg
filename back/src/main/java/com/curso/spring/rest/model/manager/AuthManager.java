package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.dao.PrivilegioDao;
import com.curso.spring.rest.model.dao.RolDao;
import com.curso.spring.rest.model.dao.UsuarioDao;
import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.entity.Usuario;
import com.curso.spring.rest.model.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link AuthService}
 */
@Service
public class AuthManager implements AuthService {

    private final UsuarioDao usuarioDao;
    private final RolDao rolDao;
    private final PrivilegioDao privilegioDao;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthManager(UsuarioDao usuarioDao, RolDao rolDao, PrivilegioDao privilegioDao, BCryptPasswordEncoder encoder) {
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
        this.privilegioDao = privilegioDao;
        this.encoder = encoder;
    }

    /**
     * @see UsuarioDao#findByUsername(String)
     */
    @Override
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    public Usuario findByClienteId(Integer clienteId) {
        return usuarioDao.findByClienteId(clienteId);
    }

    @Override
    public Rol findByRol(String rol) {
        return rolDao.findByRol(rol);
    }

    @Override
    public Privilegio findByPrivilegio(String privilegio) {
        return privilegioDao.findByPrivilegio(privilegio);
    }

    /**
     * Guarda un usuario y lo retorna, ya que el guardado ha podido modificar la instancia.
     *
     * @param usuario usuario a guardar, no puede ser {@literal null}
     * @returnel el usuario guardado
     */
    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    /**
     * Crea un usuario para un cliente
     *
     * @param usuario usuario a guardar
     * @return usuario guardado
     */
    @Override
    public Usuario saveCliente(Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.addRol(findByRol("ROLE_CLIENTE"));
        return usuarioDao.save(usuario);
    }

    /**
     * @param usuario usuario a eliminar
     */
    @Override
    public void deleteUsuario(Usuario usuario) {
        try {
            this.usuarioDao.delete(usuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param rol el rol a guardar
     * @return el rol guardado
     */
    @Override
    public Rol saveRol(Rol rol) {
        return rolDao.save(rol);
    }

    /**
     * @param privilegio el privilegio a guardar
     * @return el privilegio guardado
     */
    @Override
    public Privilegio savePrivilegio(Privilegio privilegio) {
        return privilegioDao.save(privilegio);
    }

    /**
     * @return el número de usuarios, -1 si ocurre algún error
     */
    @Override
    public long countUsuarios() {
        try {
            return usuarioDao.count();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * @see RolDao#count()
     *
     * @return el número de roles
     */
    @Override
    public long countRoles() {
        return rolDao.count();
    }
}
