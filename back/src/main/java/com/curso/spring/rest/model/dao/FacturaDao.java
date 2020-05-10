package com.curso.spring.rest.model.dao;


import com.curso.spring.rest.model.entity.Cliente;
import com.curso.spring.rest.model.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz para hacer operaciones con {@link Factura} en la base de datos
 */
@Repository
public interface FacturaDao extends JpaRepository<Factura, Integer> {

    /**
     * Obtiene una factura con los items asociacios
     * @param id id de la factura a obtener
     * @return factura con los items asociados
     */
    @Query(value = "select f from Factura f join fetch f.cliente c join fetch f.items i join fetch i.producto where f.id = ?1")
    Factura fetchByIdWithClienteWithItemFacturaWithProducto(Integer id);

    /**
     * Obtiene un listado paginado con las facturas de un cliente
     * @param cliente cliente del que se obtienen las facturas
     * @param pageable paginación
     * @return página con facturas de un cliente
     */
    Page<Factura> findAllFacturasByClienteOrderById(Cliente cliente, Pageable pageable);

    /**
     * Obtiene un listado con todas las facturas de un cliente
     * @param cliente cliente del que se obtienen las facturas
     * @return lista de facturas del cliente
     */
    List<Factura> findAllByCliente(Cliente cliente);

    /**
     * Cuenta el total de facturas de un cliente
     * @param clienteId id del cliente del que se van a contar las facturas
     * @return total de facturas del cliente
     */
    Long countAllByClienteId(Integer clienteId);
}
