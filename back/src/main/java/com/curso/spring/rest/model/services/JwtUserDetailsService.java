package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Privilegio;
import com.curso.spring.rest.model.entity.Rol;
import com.curso.spring.rest.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final AuthService authService;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public JwtUserDetailsService(AuthService authService, BCryptPasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = this.authService.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
        else if (usuario.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("El usuario no puede iniciar sesión");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, this.getAuthorities(usuario.getRoles()));
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        usuario.setPassword(this.encoder.encode(usuario.getPassword()));
        usuario.addRol(this.authService.findByRol("ROLE_USER"));
        return this.authService.saveUsuario(usuario);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Rol> roles) {
        return this.getGrantedAuthorities(this.getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Rol> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilegio> collection = new ArrayList<>();
        for (Rol rol: roles) {
            collection.addAll(rol.getPrivilegios());
        }
        for (Privilegio privilegio: collection) {
            privileges.add(privilegio.getPrivilegio());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilegio: privileges) {
            authorities.add(new SimpleGrantedAuthority(privilegio));
        }
        return authorities;
    }
}
