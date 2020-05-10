package com.curso.spring.rest.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entidad que representa un producto en la base de datos
 */
@Entity
@Table(schema = "tfg", name = "productos")
public class Producto implements Serializable {

    private static final long serialVersionUID = -1337640584244361382L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "no puede ser null")
    @Column(unique = true)
    private String nombre;

    @Min(value = 1, message = "tiene que ser mayor que 0")
    @NotNull(message = "es obligatorio")
    private BigDecimal precio;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    public Producto() {}

    // constructor que hace una copia del producto
    public Producto(Producto producto) {
        setId(producto.id);
        setNombre(producto.nombre);
        setPrecio(producto.precio);
        setCreatedAt(producto.createdAt);
    }

    /**
     * Instancia una nueva fecha antes de persistir en la base de datos
     * todo comprobar si no modifica la fecha al actualizar el producto
     */
    @PrePersist
    private void prePersist() {
        createdAt = new Date();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Copia todos los atributos de un producto menos la id
     * @param producto producto que se va a copiat
     */
    public void copy(Producto producto) {
        precio = producto.precio;
        nombre = producto.nombre;
        createdAt = producto.createdAt;
    }
}
