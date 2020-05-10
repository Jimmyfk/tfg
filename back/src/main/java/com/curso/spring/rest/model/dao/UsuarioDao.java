package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz para hacer operaciones con la entidad {@link Usuario} en la base de datos
 */
@Repository
public interface UsuarioDao extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por nombre
     * @param username nombre del usuario a buscar
     * @return el usuario encontrado
     */
    Usuario findByUsername(String username);

    /**
     * Elimina un usuario
     * @param entity usuario a eliminar
     */
    @Override
    void delete(Usuario entity);
}
