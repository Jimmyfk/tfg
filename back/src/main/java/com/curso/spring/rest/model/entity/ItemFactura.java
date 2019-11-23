package com.curso.spring.rest.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.math.BigDecimal;

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

    @Min(value = 1, message = "tiene que ser mayor que 0")
    private Integer cantidad;

    private BigDecimal importe;

    public ItemFactura() {}

    public ItemFactura(Integer cantidad, Producto producto) {
        this.cantidad = cantidad;
        this.producto = producto;
        setImporte(getPrecioPorCantidad());
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

    public BigDecimal getImporte() {
        return this.importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public void calcularImporte() {
        setImporte(getPrecioPorCantidad());
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    private BigDecimal getPrecioPorCantidad() {
        return getProducto().getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }
}
