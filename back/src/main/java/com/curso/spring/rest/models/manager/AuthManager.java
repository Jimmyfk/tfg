package com.curso.spring.rest.models.manager;

import com.curso.spring.rest.models.dao.PrivilegioDao;
import com.curso.spring.rest.models.dao.RolDao;
import com.curso.spring.rest.models.dao.UsuarioDao;
import com.curso.spring.rest.models.entity.Privilegio;
import com.curso.spring.rest.models.entity.Rol;
import com.curso.spring.rest.models.entity.Usuario;
import com.curso.spring.rest.models.services.AuthService;
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
}
