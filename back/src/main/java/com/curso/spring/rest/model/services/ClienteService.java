package com.curso.spring.rest.model.services;

import com.curso.spring.rest.model.entity.Cliente;
import export.ClienteList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

/**
 * Servicio para hacer operaciones con clientes, es el que será usado en los controladores
 */
public interface ClienteService {

    /**
     * Devuelve un listado con todos los clientes
     *
     * @return todos los clientes
     */
    List<Cliente> findAll();

    /**
     * Devuelve una página con los clientes
     *
     * @param pageable implementación pageable
     * @return página con los clientes
     */
    Page<Cliente> findAll(Pageable pageable);

    /**
     * Guarda un cliente y lo retorna
     *
     * @param cliente cliente a guardar
     * @return el cliente guardado
     */
    Cliente save(Cliente cliente);

    /**
     * Busca un cliente por su id
     *
     * @param id id del cliente
     * @return el cliente
     */
    Cliente findById(Integer id);

    /**
     * Comprueba si un cliente existe buscando por su id
     *
     * @param id id del cliente a buscar
     * @return true si el cliente existe, false si no existe
     */
    boolean exists(Integer id);

    /**
     * Devuelve un listado de clientes que ser� usado en paginaci�n
     *
     * @param page n�mero de la p�gina
     * @param size cantidad de clientes por pagina
     * @return listado de clientes
     */
    ClienteList index(int page, int size);

    /**
     * Exporta un listado con los clientes a xml
     *
     * @return listado de clientes
     */
    ClienteList export();

    ResponseEntity<?> show(Integer id);

    ResponseEntity<?> getUsuario(Integer clienteId);

    ResponseEntity<?> create(Cliente cliente, BindingResult result, String password);

    ResponseEntity<?> update(Cliente cliente, BindingResult result, Integer id);

    ResponseEntity<?> remove(Integer id);

    ResponseEntity<?> existenClientes();

    Long countAll();
}
