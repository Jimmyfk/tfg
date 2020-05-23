package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.dao.PrivilegioDao;
import com.curso.spring.rest.model.dao.RolDao;
import com.curso.spring.rest.model.dao.UsuarioDao;
import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.entity.Usuario;
import com.curso.spring.rest.model.services.AuthService;
import com.curso.spring.rest.model.services.ErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementación de {@link AuthService}
 */
@Service
public class AuthManager implements AuthService {

    private final UsuarioDao usuarioDao;
    private final RolDao rolDao;
    private final PrivilegioDao privilegioDao;
    private final ErrorService errorService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthManager(UsuarioDao usuarioDao, RolDao rolDao, PrivilegioDao privilegioDao, ErrorService errorService, BCryptPasswordEncoder encoder) {
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
        this.privilegioDao = privilegioDao;
        this.errorService = errorService;
        this.encoder = encoder;
    }

    /**
     * @see UsuarioDao#findByUsername(String)
     */
    @Override
    public Usuario findByUsername(String username) {
        return this.usuarioDao.findByUsername(username);
    }

    @Override
    public Usuario findByClienteId(Integer clienteId) {
        return this.usuarioDao.findByClienteId(clienteId);
    }

    @Override
    public Rol findByRol(String rol) {
        return this.rolDao.findByRol(rol);
    }

    @Override
    public Privilegio findByPrivilegio(String privilegio) {
        return this.privilegioDao.findByPrivilegio(privilegio);
    }

    /**
     * Guarda un usuario y lo retorna, ya que el guardado ha podido modificar la instancia.
     *
     * @param usuario usuario a guardar, no puede ser {@literal null}
     * @return el el usuario guardado
     */
    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return this.usuarioDao.save(usuario);
    }

    /**
     * {@inheritDoc}
     *
     * @param usuarioId id del usuario que se va a modificar
     * @param password  nueva contraseña del usuario
     * @return respuesta con el resultado de la operación
     */
    @Transactional
    @Override
    public ResponseEntity<?> actualizarPassword(Integer usuarioId, String password) {
        Map<String, Object> body = new HashMap<>();
        ResponseEntity<?> resultado = null;

        try {
            int res = this.usuarioDao.updatePassword(usuarioId, password);
            if (res == 1) {
                body.put("mensaje", "Contraseña actualizada correctamente");
                resultado = ResponseEntity.ok(body);
            } else {
                body.put("mensaje", "Error al actualizar la contraseña");
                resultado = ResponseEntity.ok(body);
            }
        } catch (DataAccessException e) {
            resultado = this.errorService.dbError(e, body);
        }
        return resultado;
    }


    /**
     * Crea un usuario para un cliente
     *
     * @param usuario usuario a guardar
     * @return usuario guardado
     */
    @Override
    public Usuario saveCliente(Usuario usuario) {
        usuario.setPassword(this.encoder.encode(usuario.getPassword()));
        usuario.addRol(this.findByRol("ROLE_CLIENTE"));
        return this.usuarioDao.save(usuario);
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
        return this.rolDao.save(rol);
    }

    /**
     * @param privilegio el privilegio a guardar
     * @return el privilegio guardado
     */
    @Override
    public Privilegio savePrivilegio(Privilegio privilegio) {
        return this.privilegioDao.save(privilegio);
    }

    /**
     * @return el número de usuarios, -1 si ocurre algún error
     */
    @Override
    public long countUsuarios() {
        try {
            return this.usuarioDao.count();
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
        return this.rolDao.count();
    }
}
