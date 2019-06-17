package com.curso.spring.rest.models.dao;


import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface FacturaDao extends JpaRepository<Factura, Long> {

    @Query(value = "select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto where f.id = ?1")
    Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

    Page<Factura> findAllFacturasByClienteOrderById(Cliente cliente, Pageable pageable);

    List<Factura> findAllByCliente(Cliente cliente);
}
