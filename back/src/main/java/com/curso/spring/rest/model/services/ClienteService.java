package com.curso.spring.rest.model.services;

import com.curso.spring.rest.exception.CustomException;
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
     * Devuelve un listado de clientes que será usado en paginación
     *
     * @param page número de la página
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

    /**
     * Busca un cliente por su id y lo añade a la respuesta, si lo encuentra
     *
     * @param id id del cliente a buscar
     * @return respuesta con el cliente, o un mensaje en el caso de que no exista
     */
    ResponseEntity<?> show(Integer id);

    /**
     * Busca un usuario por su id de cliente y lo añade a la respuesta
     *
     * @param clienteId id del cliente
     * @return respuesta con el usuario
     */
    ResponseEntity<?> getUsuario(Integer clienteId);

    /**
     * Guarda un cliente en la base de datos y después le crea una cuenta de usuario
     *
     * @param cliente cliente a guardar
     * @param result resultado de la validación
     * @param password contraseña de la cuenta de usuario
     * @return respuesta con el cliente creado, o con el mensaje de error en caso de que ocurra alguno
     */
    ResponseEntity<?> create(Cliente cliente, BindingResult result, String password);

    /**
     * Actualiza un cliente en la base de datos TODO: 17/05/2020 - Revisar update del usuario
     *
     * @param cliente cliente a actualizar
     * @param result resultado de la validación
     * @param id id del cliente
     * @return respuesta con el cliente
     */
    ResponseEntity<?> update(Cliente cliente, BindingResult result, Integer id) throws CustomException;

    /**
     * Borra un cliente de la base de datos
     *
     * @param id id del cliente a eliminar
     * @return respuesta con el mensaje del resultado de la operación
     * @throws CustomException si el cliente tiene facturas
     */
    ResponseEntity<?> remove(Integer id) throws CustomException;

    /**
     * Comprueba si hay clientes en la base de datos
     *
     * @return respuesta indicando si hay clientes o no
     */
    ResponseEntity<?> existenClientes();
}
