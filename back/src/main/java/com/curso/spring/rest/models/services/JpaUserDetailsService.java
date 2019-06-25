package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.dao.UsuarioDao;
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
import java.util.List;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioDao.findByUsername(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("El usuario no existe");
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Rol rol : usuario.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(rol.getAuthority()));
        }

        if (authorities.isEmpty()) {
            throw new UsernameNotFoundException("El usuario no puede iniciar sesión");
        }
        return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, authorities);
    }

    public Usuario save(Usuario usuario) {
        usuario.setPassword(encoder.encode(usuario.getPassword()));
        return usuarioDao.save(usuario);
    }
}
