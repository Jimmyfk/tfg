package com.curso.spring.rest.models.services;

import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public interface FacturaService {

    @Query(value = "select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto where f.id = ?1")
    Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

    Page<Factura> findAllFacturasByClienteOrderById(Cliente cliente, Pageable pageable);

    List<Factura> findAllByCliente(Cliente cliente);

    Factura save(Factura factura);

    Factura findById(Long id);

    void delete(Long id);
















}
