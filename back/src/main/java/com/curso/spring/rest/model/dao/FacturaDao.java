package com.curso.spring.rest.model.dao;


import com.curso.spring.rest.model.entity.Cliente;
import com.curso.spring.rest.model.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaDao extends JpaRepository<Factura, Integer> {

    @Query(value = "select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto where f.id = ?1")
    Factura fetchByIdWithClienteWithItemFacturaWithProducto(Integer id);

    Page<Factura> findAllFacturasByClienteOrderById(Cliente cliente, Pageable pageable);

    List<Factura> findAllByCliente(Cliente cliente);

    Long countAllByClienteId(Integer clienteId);
}
