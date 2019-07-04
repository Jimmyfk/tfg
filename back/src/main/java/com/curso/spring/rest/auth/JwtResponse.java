package com.curso.spring.rest.auth;

import javax.servlet.http.Cookie;
import java.io.Serializable;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -6927202016075037892L;

    private final Cookie cookie;

    public JwtResponse(Cookie cookie) {
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        return cookie;
    }
}
