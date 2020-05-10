package com.curso.spring.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestApplication {

    /**
     * Clase principal de la aplicación Spring
     * @param args parámetros para lanzar la aplicación, en este caso no usaremos ninguno
     */

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }

}
