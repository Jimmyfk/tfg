package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.dao.ClienteDao;
import com.curso.spring.rest.model.entity.Cliente;
import com.curso.spring.rest.model.services.ClienteService;
import com.curso.spring.rest.model.services.ErrorService;
import export.ClienteList;
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

@Service
public class ClienteManager implements ClienteService {

    private final ClienteDao clienteDao;
    private final ErrorService errorService;

    @Autowired
    public ClienteManager(ClienteDao clienteDao, ErrorService errorService) {
        this.clienteDao = clienteDao;
        this.errorService = errorService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteDao.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Integer id) {
        return clienteDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        clienteDao.deleteById(id);
    }

    @Override
    @Transactional
    public void delete(Cliente cliente) {
        clienteDao.delete(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(Integer id) {
        return clienteDao.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteList index(int page, int size) {
        return new ClienteList(this.findAll(PageRequest.of(page, size)).getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteList export() {
        return new ClienteList(this.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> show(Integer id) {
        Cliente cliente;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = this.findById(id);
        } catch(DataAccessException e) {
            return errorService.dbError(e, response);
        }

        if(cliente == null) {
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(cliente);
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(@Valid Cliente cliente, BindingResult result) {
        Cliente clienteNew;
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors())
            return errorService.throwErrors(result, response);

        try {
            clienteNew = this.save(cliente);
        } catch(DataAccessException e) {
            return errorService.dbError(e, response);
        }

        response.put("mensaje", "El cliente ha sido creado con éxito");
        response.put("cliente", clienteNew);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> update(@Valid Cliente cliente, BindingResult result, Integer id) {
        Cliente clienteActual = this.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (result.hasErrors())
            return errorService.throwErrors(result, response);

        if (clienteActual == null) {
            response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
                    .concat(id.toString().concat(" no existe")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try {
            clienteActual.copy(cliente);
            clienteActual = this.save(clienteActual);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }

        response.put("mensaje", "El cliente ha sido actualizado con éxito!");
        response.put("cliente", clienteActual);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> remove(Integer id) {
        Map<String, Object> response = new HashMap<>();

        try {
            this.delete(id);
        } catch (DataAccessException e) {
            return errorService.dbError(e, response);
        }

        response.put("mensaje", "Cliente eliminado con éxito");

        return ResponseEntity.ok(response);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> existenClientes() {
        Map<String, Object> body = new HashMap<>();
        try {
            Integer numCLientes = countAll();
            body.put("existenClientes", numCLientes > 0);
        } catch (Exception e) {
            e.printStackTrace();
            body.put("existenClientes", false);
        }
        return ResponseEntity.ok(body);
    }

    @Override
    public Integer countAll() {
        return clienteDao.findAll().size();
    }

}
