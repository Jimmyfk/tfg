package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.entity.Privilegio;
import com.curso.spring.rest.models.entity.Rol;
import com.curso.spring.rest.models.entity.Usuario;
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

    private AuthService authService;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public JwtUserDetailsService(AuthService authService, BCryptPasswordEncoder encoder) {
        this.authService = authService;
        this.encoder = encoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = authService.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario no existe");
        }
        else if (usuario.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("El usuario no puede iniciar sesión");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, getAuthorities(usuario.getRoles()));
    }

    public Usuario save(Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        usuario.addRol(authService.findByRol("ROLE_USER"));
        return authService.saveUsuario(usuario);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Rol> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
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
