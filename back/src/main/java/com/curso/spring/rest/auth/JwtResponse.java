package com.curso.spring.rest.auth;

import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -6927202016075037892L;

    private final String jwtToken;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
