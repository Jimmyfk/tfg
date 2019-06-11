package com.curso.spring.rest.models.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "clientes", schema = "db_springboot")
@SequenceGenerator(name = "id_cliente", schema = "db_springboot", allocationSize = 1)
public class Cliente  implements Serializable {

    private static final long serialVersionUID = 548693643152216215L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_cliente")
    private Long id;

    private String nombre;
    private String apellidos;
    private String email;

    @Column(name = "created_at")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void copy(Cliente cliente) {
        nombre = cliente.nombre;
        apellidos = cliente.apellidos;
        email = cliente.email;
        createdAt = cliente.createdAt;
    }

    @PrePersist
    public void prePersist() {
        createdAt = new Date();
    }
}
