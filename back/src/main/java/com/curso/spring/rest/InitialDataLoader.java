package com.curso.spring.rest;

import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Clase que crea los roles y privilegios la primera vez que se lanza la aplicación
     */

    // variable para controlar si ya se han creado los roles
    private boolean alreadySetup;
    private final AuthService authService;

    @Autowired
    public InitialDataLoader(AuthService authService) {
        this.authService = authService;
        this.isSetup();
    }

    /**
     * Método que crea los privilegios y roles en el caso de que no existan
     * @param event
     */
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // si ya hay algun rol creado, retornamos para no volver a ejecutar el proceso
        if (this.alreadySetup)
            return;
        // si no hay roles creados, se crean los privilegios de escritura y lectura y los roles de administrador y usuario
        Privilegio read = this.createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilegio write = this.createPrivilegeIfNotFound("WRITE_PRIVILEGE");
        Privilegio root = this.createPrivilegeIfNotFound("SUPER_PRIVILEGE");

        List<Privilegio> adminPrivileges = Arrays.asList(read, write, root);
        List<Privilegio> clientePrivileges = Arrays.asList(read, write);
        this.createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        this.createRoleIfNotFound("ROLE_USER", Collections.singletonList(read));
        this.createRoleIfNotFound("ROLE_CLIENTE", clientePrivileges);

        this.alreadySetup = true;
    }

    /**
     * Crea un privilegio si no existe
     * @param name nombre del privilegio
     * @return el privilegio nuevo o existente en la base de dato
     */
    @Transactional
    protected Privilegio createPrivilegeIfNotFound(String name) {
        Privilegio privilegio = this.authService.findByPrivilegio(name);
        if (privilegio == null) {
            privilegio = this.authService.savePrivilegio(new Privilegio(name));
        }
        return privilegio;
    }

    /**
     * Crea un rol y le asigna los privilegios, si el rol no existe
     * @param name nombre del rol
     * @param privilegios lista de privilegios del rol
     * @return rol nuevo o existente en la base de datos
     */
    @Transactional
    protected Rol createRoleIfNotFound(String name, Collection<Privilegio> privilegios) {
        Rol rol = this.authService.findByRol(name);
        if (rol == null) {
            rol = new Rol(name);
            rol.setPrivilegios(privilegios);
            rol = this.authService.saveRol(rol);
        }
        return rol;
    }

    /**
     * Comprueba si ya existe algun rol en la base de datos
     */
    private void isSetup() {
        this.alreadySetup = this.authService.countRoles() > 0;
    }
}
