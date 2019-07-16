package com.curso.spring.rest.models.manager;

import com.curso.spring.rest.models.dao.FacturaDao;
import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.entity.Factura;
import com.curso.spring.rest.models.services.ClienteService;
import com.curso.spring.rest.models.services.ErrorService;
import com.curso.spring.rest.models.services.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacturaManager implements FacturaService {

    private final FacturaDao facturaDao;
    private final ClienteService clienteService;
    private final ErrorService errorService;

    @Autowired
    public FacturaManager(FacturaDao facturaDao, ClienteService clienteService, ErrorService errorService) {
        this.facturaDao = facturaDao;
        this.clienteService = clienteService;
        this.errorService = errorService;
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Integer id) {
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Factura> findAllFacturasByClienteOrderById(Cliente cliente, Pageable pageable) {
        return facturaDao.findAllFacturasByClienteOrderById(cliente, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findAllByCliente(Cliente cliente) {
        return facturaDao.findAllByCliente(cliente);
    }

    @Override
    @Transactional
    public Factura save(Factura factura) {
        return facturaDao.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findById(Integer id) {
        return facturaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        facturaDao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> show(Integer id) {
        Factura factura;
        Map<String, Object> response = new HashMap<>();

        try {
            factura = this.fetchByIdWithClienteWithItemFacturaWithProducto(id);
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }

        if (factura == null) {
            response.put("mensaje", "La factura " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<?> getFacturasCliente(Integer id) {
        List<Factura> facturas;
        Map<String, Object> response = new HashMap<>();

        try {
            facturas = this.findAllByCliente(this.clienteService.findById(id));
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }

        return ResponseEntity.ok(facturas);
    }

    @Override
    @Transactional
    public ResponseEntity<?> create(Integer id, Factura factura) {
        Cliente cli;
        Map<String, Object> response = new HashMap<>();

        try {
            cli = this.clienteService.findById(id);
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }

        if (cli == null) {
            response.put("error", "El cliente " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        this.save(factura);
        response.put("factura", factura);
        response.put("mensaje", "Factura creada correctamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Override
    @Transactional
    public ResponseEntity<?> remove(Integer id) {
        Factura factura = this.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (factura != null) {
            try {
                this.delete(id);
            } catch (DataAccessException e) {
                return errorService.dbError(e, response);
            }
            response.put("mensaje", "Factura eliminada");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        response.put("error", "La factura no existe");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
