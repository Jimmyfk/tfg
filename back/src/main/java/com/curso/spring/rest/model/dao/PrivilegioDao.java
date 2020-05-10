package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Privilegio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para hacer operaciones con la entidad {@link Privilegio} en la base de datos
 */
@Repository
public interface PrivilegioDao extends JpaRepository<Privilegio, Integer> {

    /**
     * Busca un privilegio por su nombre
     * @param privilegio nombre del privilegio a buscar
     * @return {@link Privilegio} si lo encuentra
     */
    Privilegio findByPrivilegio(String privilegio);
}
