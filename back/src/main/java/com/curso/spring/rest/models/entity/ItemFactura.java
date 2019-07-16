package com.curso.spring.rest.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "tfg", name = "facturas_items")
public class  ItemFactura implements Serializable {

    private static final long serialVersionUID = -1142856044917006208L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
    private Producto producto;

    private Double precio;

    private Integer cantidad;

    private Double importe;

    public ItemFactura() {}

    public ItemFactura(Integer cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.precio = producto.getPrecio();
        importe = cantidad * precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getImporte() {
        if (importe != null)
            return this.importe;
        return cantidad * producto.getPrecio();
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
