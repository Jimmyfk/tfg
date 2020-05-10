package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para hacer operaciones con la entidad {@link Rol} en la base de datos
 */
@Repository
public interface RolDao extends JpaRepository<Rol, Integer> {

    /**
     * Busca un rol por nombre
     * @param rol nombre del rol a buscar
     * @return el rol encontrado
     */
    Rol findByRol(String rol);
}
