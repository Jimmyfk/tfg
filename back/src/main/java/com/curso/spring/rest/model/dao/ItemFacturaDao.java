package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.ItemFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para hacer operaciones con la entidad {@link ItemFactura} en la base de datos
 */
@Repository
public interface ItemFacturaDao extends JpaRepository<ItemFactura, Long> {

    /**
     * Cuenta la cantidad de items que existen con un producto
     * @param productoId id del producto
     * @return la cantidad de items que existen con el producto
     */
    Long countAllByProductoId(Integer productoId);
}
