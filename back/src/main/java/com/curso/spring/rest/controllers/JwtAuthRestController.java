package com.curso.spring.rest.controllers;
import com.curso.spring.rest.auth.JwtRequest;
import com.curso.spring.rest.auth.JwtResponse;
import com.curso.spring.rest.auth.JwtTokenUtil;
import com.curso.spring.rest.model.entity.Usuario;
import com.curso.spring.rest.model.services.AuthService;
import com.curso.spring.rest.model.services.ErrorService;
import com.curso.spring.rest.model.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/auth")
public class JwtAuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final AuthService authService;
    private final ErrorService errorService;

    @Autowired
    public JwtAuthRestController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                 JwtUserDetailsService userDetailsService, AuthService authService,
                                 ErrorService errorService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
        this.errorService = errorService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest)  {
        try {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
        } catch (BadCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(this.jwtTokenUtil.generateToken(userDetails, authService.findByUsername(authRequest.getUsername()).getRoles())));
    }

    @PostMapping(value = {"/register"})
    public ResponseEntity<?> saveUser(@RequestBody Usuario user) {
        final Usuario usuario;
        Map<String, Object> response = new HashMap<>();
        if (firstUser()) {
            user.addRol(authService.findByRol("ROLE_ADMIN"));
        }

        try {
            usuario = this.userDetailsService.save(user);
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }

        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        response.put("usuario", usuario);
        response.put("mensaje", "Usuario " + usuario.getUsername() + " registrado con éxito");
        return ResponseEntity.accepted().body(response);
    }

    @DeleteMapping(value = "/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId) {
        return null;
    }

    private void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("INVALID_CREDENTIALS", e);
        }
    }

    private boolean firstUser() {
        return authService.countUsuarios() == 0;
    }
}
