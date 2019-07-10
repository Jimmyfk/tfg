package com.curso.spring.rest.controllers;
import com.curso.spring.rest.auth.JwtRequest;
import com.curso.spring.rest.auth.JwtResponse;
import com.curso.spring.rest.auth.JwtTokenUtil;
import com.curso.spring.rest.models.entity.Usuario;
import com.curso.spring.rest.models.services.AuthService;
import com.curso.spring.rest.models.services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping(value = "/api/auth")
public class JwtAuthRestController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final JwtUserDetailsService userDetailsService;
    private final AuthService authService;

    @Autowired
    public JwtAuthRestController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                                 JwtUserDetailsService userDetailsService, AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest)  {
        try {
            authenticate(authRequest.getUsername(), authRequest.getPassword());
        } catch (BadCredentialsException e) {
            return ResponseEntity.notFound().build();
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        return ResponseEntity.ok(new JwtResponse(new Cookie("token", "Bearer " + jwtTokenUtil.generateToken(userDetails, authService.findByUsername(authRequest.getUsername()).getRoles()))));
    }

    @PostMapping(value = "/register/{admin}")
    public ResponseEntity<?> saveUser(@RequestBody Usuario user, @PathVariable(required = false) boolean admin) {
        if (admin || firstUser()) {
            user.addRol(authService.findByRol("ROLE_ADMIN"));
        }
        return ResponseEntity.ok(userDetailsService.save(user));
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
