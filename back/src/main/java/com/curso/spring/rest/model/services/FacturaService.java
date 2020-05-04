package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Cliente;
import com.curso.spring.rest.model.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;


@Service
public interface FacturaService {

    @Query(value = "select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto where f.id = ?1")
    Factura fetchByIdWithClienteWithItemFacturaWithProducto(Integer id);

    Page<Factura> findAllFacturasByClienteOrderById(Cliente cliente, Pageable pageable);

    List<Factura> findAllByCliente(Cliente cliente);

    Factura save(Factura factura);

    Factura findById(Integer id);

    void delete(Integer id);

    ResponseEntity<?> show(Integer id);

    ResponseEntity<?> getFacturasCliente(Integer id);

    ResponseEntity<?> create(Integer id, Factura factura, BindingResult bindingResult);

    ResponseEntity<?> remove(Integer id);

    ResponseEntity<?> exportPdf(Integer facturaId);
















}
