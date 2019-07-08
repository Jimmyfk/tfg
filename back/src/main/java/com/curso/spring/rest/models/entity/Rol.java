package com.curso.spring.rest.models.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(schema = "tfg", name = "roles")
public class Rol implements Serializable {

    private static final long serialVersionUID = 5195148737062532825L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rol;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "privilegios_roles",
            joinColumns = @JoinColumn(
                    name = "rol_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilegio_id", referencedColumnName = "id"))
    private Collection<Privilegio> privilegios;

    public Rol() {}

    public Rol(String rol) {
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public Collection<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(Collection<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }
}
