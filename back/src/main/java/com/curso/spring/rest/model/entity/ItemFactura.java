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

/**
 * Entidad que representa un item de la factura en la base de datos
 */
@Entity
@Table(schema = "tfg", name = "facturas_items")
public class ItemFactura implements Serializable {

    private static final long serialVersionUID = -1142856044917006208L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relación many to one con producto
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Producto producto;

    @Min(value = 1, message = "tiene que ser mayor que 0")
    private Integer cantidad;

    private BigDecimal precio;

    public ItemFactura() {}

    public ItemFactura(Integer cantidad, Producto producto) {
        this.setCantidad(cantidad);
        this.setProducto(producto);
        this.setPrecio(producto.getPrecio());
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecio() {
        return this.precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Producto getProducto() {
        return this.producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /**
     * Calcula el importe
     * @return precio * cantidad
     */
    public BigDecimal getImporte() {
        return this.precio.multiply(BigDecimal.valueOf(this.cantidad));
    }
}
