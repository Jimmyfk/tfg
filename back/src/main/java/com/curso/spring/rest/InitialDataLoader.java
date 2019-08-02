package com.curso.spring.rest;

import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup;
    private AuthService authService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public InitialDataLoader(AuthService authService, BCryptPasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
        this.isSetup();
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;
        Privilegio read = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilegio write = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilegio> adminPrivileges = Arrays.asList(read, write);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(read));

        alreadySetup = true;
    }

    @Transactional
    protected Privilegio createPrivilegeIfNotFound(String name) {
        Privilegio privilegio = this.authService.findByPrivilegio(name);
        if (privilegio == null) {
            privilegio = this.authService.savePrivilegio(new Privilegio(name));
        }
        return privilegio;
    }

    @Transactional
    protected Rol createRoleIfNotFound(String name, Collection<Privilegio> privilegios) {
        Rol rol = this.authService.findByRol(name);
        if (rol == null) {
            rol = new Rol(name);
            rol.setPrivilegios(privilegios);
            this.authService.saveRol(rol);
        }
        return rol;
    }

    private void isSetup() {
        this.alreadySetup = this.authService.countRoles() > 0;
    }
}
