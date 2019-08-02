package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.dao.PrivilegioDao;
import com.curso.spring.rest.model.dao.RolDao;
import com.curso.spring.rest.model.dao.UsuarioDao;
import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.entity.Usuario;
import com.curso.spring.rest.model.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthManager implements AuthService {

    private UsuarioDao usuarioDao;
    private RolDao rolDao;
    private PrivilegioDao privilegioDao;

    @Autowired
    public AuthManager(UsuarioDao usuarioDao, RolDao rolDao, PrivilegioDao privilegioDao) {
        this.usuarioDao = usuarioDao;
        this.rolDao = rolDao;
        this.privilegioDao = privilegioDao;
    }

    @Override
    public Usuario findByUsername(String username) {
        return usuarioDao.findByUsername(username);
    }

    @Override
    public Rol findByRol(String rol) {
        return rolDao.findByRol(rol);
    }

    @Override
    public Privilegio findByPrivilegio(String privilegio) {
        return privilegioDao.findByPrivilegio(privilegio);
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        return usuarioDao.save(usuario);
    }

    @Override
    public Rol saveRol(Rol rol) {
        return rolDao.save(rol);
    }

    @Override
    public Privilegio savePrivilegio(Privilegio privilegio) {
        return privilegioDao.save(privilegio);
    }

    @Override
    public long countUsuarios() {
        return usuarioDao.count();
    }

    @Override
    public long countRoles() {
        return rolDao.count();
    }
}
