package com.curso.spring.rest.auth;

import javax.servlet.http.Cookie;
import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -6927202016075037892L;

    private final String jwtToken;
    private final Cookie cookie;

    public JwtResponse(Cookie cookie) {
        this.jwtToken = cookie.getValue();
        this.cookie = cookie;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
