package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.exception.CustomException;
import com.curso.spring.rest.exception.errors.RestApiErrorCode;
import com.curso.spring.rest.model.dao.ClienteDao;
import com.curso.spring.rest.model.dao.FacturaDao;
import com.curso.spring.rest.model.entity.Cliente;
import com.curso.spring.rest.model.entity.Usuario;
import com.curso.spring.rest.model.services.AuthService;
import com.curso.spring.rest.model.services.ClienteService;
import com.curso.spring.rest.model.services.ErrorService;
import export.ClienteList;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  El manager es la implementación de un servicio
 */
@Service
public class ClienteManager implements ClienteService {

    private final ClienteDao clienteDao;
    private final FacturaDao facturaDao;
    private final ErrorService errorService;
    private final AuthService authService;

    /**
     * Constructor que inyecta las dependencias
     * @see Autowired
     *
     * @param clienteDao dao para hacer operaciones con clientes
     * @param errorService servicio para manejar errores
     * @param facturaDao dao para hacer operaciones con facturas
     * @param authService servicio de autenticación
     */
    @Autowired
    public ClienteManager(ClienteDao clienteDao, ErrorService errorService, FacturaDao facturaDao, AuthService authService) {
        this.clienteDao = clienteDao;
        this.facturaDao = facturaDao;
        this.errorService = errorService;
        this.authService = authService;
    }

    /**
     * {@inheritDoc }
     *
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return this.clienteDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return this.clienteDao.findAll(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return this.clienteDao.save(cliente);
    }

    /**
     * {@inheritDoc}
     *
     * @param id id del cliente
     * @return el cliente, si lo encuentra, null si no lo encuentra
     */
    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Integer id) {
        return this.clienteDao.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     *
     * @param id id del cliente a buscar
     * @return true si el cliente existe, false si no
     */
    @Override
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return clienteDao.existsById(id);
    }

    /**
     * {@inheritDoc}
     *
     * @param page número de la página
     * @param size cantidad de clientes por pagina
     * @return página con los clientes
     */
    @Override
    public ClienteList index(int page, int size) {
        return new ClienteList(this.findAll(PageRequest.of(page, size)).getContent());
    }

    /**
     * {@inheritDoc}
     *
     * @return listado con todos los clientes
     */
    @Override
    public ClienteList export() {
        return new ClienteList(this.findAll());
    }

    /**
     * {@inheritDoc}
     *
     * @param id id del cliente a buscar
     * @return respuesta con el cliente, mensaje si no lo encuentra o ocurre algún error
     */
    @Override
    public ResponseEntity<?> show(Integer id) {
        ResponseEntity<?> result = null;
        boolean finished = false;
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            // buscamos el cliente
            cliente = this.findById(id);
        } catch (DataAccessException e) {
            // si ocure una excepción, añadimos el mensaje de error a la respuesta
            result = this.errorService.dbError(e, response);
            finished = true;
        }

        if (!finished) {
            if (cliente == null) {
                // si el cliente es null, no existe
                response.put("mensaje", "El cliente ID: " + id.toString() + " no existe");
                result = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            } else {
                // si existe, lo añadimos a la respuesta
                result = ResponseEntity.ok(cliente);
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @param clienteId id del cliente
     * @return respuesta con el usuario
     */
    @Override
    public ResponseEntity<?> getUsuario(Integer clienteId) {
        Map<String, Object> response = new HashMap<>();

        // obtenemos el usuario
        Usuario usuario = this.authService.findByClienteId(clienteId);
        usuario.setPassword("");

        // añadimos el usuario a la respuesta
        response.put("usuario", usuario);

        return ResponseEntity.ok(response);
    }

    /**
     * {@inheritDoc}
     *
     * @param cliente  cliente a guardar
     * @param result   resultado de la validación
     * @param password contraseña de la cuenta de usuario
     * @return respuesta con el resultado de la operación: cliente si no hay errores, mensajes de validacion si hay algun error de validacion, o mensaje con error de base de datos
     */
    @Override
    public ResponseEntity<?> create(@Valid Cliente cliente, BindingResult result, String password) {
        ResponseEntity<?> res = null;
        boolean finished = false;
        Cliente clienteNew = null;
        Map<String, Object> response = new HashMap<>();

        // si hay erores en la validación, la respuesta serán los mensajes de validación
        if (result.hasErrors()) {
            res = this.errorService.throwErrors(result, response);
        } else {

            try {
                // guardamos el cliente
                clienteNew = this.save(cliente);
                // creamos un usuario para el cliente
                Usuario usuario = new Usuario(clienteNew, password);
                // guardamos el usuario
                this.authService.saveCliente(usuario);
            } catch (DataAccessException e) {
                // si ocurre alguna excepción, añadimos a la respuesta los mensajes de error
                res = this.errorService.dbError(e, response);
                finished = true;
            }

            if (!finished) {
                response.put("mensaje", "El cliente ha sido creado con éxito");
                response.put("cliente", clienteNew);
                res = new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }

        return res;
    }

    /**
     * {@inheritDoc}
     *
     * @param cliente cliente a actualizar
     * @param result  resultado de la validación
     * @param id      id del cliente
     * @return respuesta con el resultado de la operación
     */
    @Override
    public ResponseEntity<?> update(@Valid Cliente cliente, BindingResult result, Integer id) {
        ResponseEntity<?> res = null;
        boolean finished = false;
        Cliente clienteActual = this.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors()) {
            // si hay errores de validación, añadimos los mensajes a la respuesta
            res = this.errorService.throwErrors(result, response);
        } else if (clienteActual == null) {
            // si el cliente no existe, añadimos el mensaje de error a la respuesta
            response.put("mensaje", "Error: no se pudo editar, el cliente ID: " + id.toString() + " no existe");
            res = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        } else {

            try {
                // copiamos el cliente recibido como parámetro, para que al guardar no cree un cliente nuevo
                clienteActual.copy(cliente);
                // guardamos el cliente
                clienteActual = this.save(clienteActual);
            } catch (DataAccessException e) {
                // si ocurre algún error, añadimos el mensaje de error a la respuesta
                res = this.errorService.dbError(e, response);
                finished = true;
            }

            if (!finished) {
                // si no hay errores, añadimos el cliente y un mensaje a la respuesta
                response.put("mensaje", "El cliente ha sido actualizado con éxito!");
                response.put("cliente", clienteActual);
                res = new ResponseEntity<>(response, HttpStatus.CREATED);
            }
        }

        return res;
    }

    /**
     * {@inheritDoc}
     *
     * @param id id del cliente a eliminar
     * @return respuesta con el mensaje del resultado de la operación
     * @throws CustomException si el cliente tiene facturas
     */
    @Override
    @Transactional
    public ResponseEntity<?> remove(Integer id) throws CustomException {
        ResponseEntity<?> result;
        Map<String, Object> response = new HashMap<>();

        // si el cliente no tiene facturas, lo eliminamos
        if (this.facturaDao.countAllByClienteId(id) == 0) {
            this.clienteDao.deleteById(id);
            response.put("mensaje", "Cliente eliminado con éxito");
            result = ResponseEntity.ok(response);
        } else {
            // si tiene facturas, lanzamos excepción
            throw new CustomException(RestApiErrorCode.CLIENTE_CON_FACTURAS);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> existenClientes() {
        Map<String, Object> body = new HashMap<>();
        try {
            long numCLientes = this.clienteDao.count();
            body.put("existenClientes", numCLientes > 0);
        } catch (Exception e) {
            e.printStackTrace();
            body.put("existenClientes", false);
        }
        return ResponseEntity.ok(body);
    }
}
