package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interfaz para hacer operaciones con la entidad {@link Producto} en la base de datos
 */
@Repository
public interface ProductoDao extends JpaRepository<Producto, Integer> {

    /**
     * Busca productos con nombre sin difenenciar mayúsculas y minúsculas
     * @param nombre nombre por el que buscar
     * @return lista con los productos encontrados
     */
    List<Producto> findByNombreLikeIgnoreCase(String nombre);

    /**
     * Buscar un producto por nombre sin diferenciar mayúsculas y minúsculas
     * @param nombre por el que buscar
     * @return {@link Producto}
     */
    Producto findByNombreIgnoreCase(String nombre);

    /**
     * Comprueba si un producto existe por nombre y diferente id
     * @param nombre nombre del producto a buscar
     * @param productoId id que no tiene que tener
     * @return true si el producto existe con diferente id, false en los demás casos
     */
    Boolean existsByNombreAndIdNot(String nombre, Integer productoId);

}
