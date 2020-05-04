package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.dao.FacturaDao;
import com.curso.spring.rest.model.entity.Cliente;
import com.curso.spring.rest.model.entity.Factura;
import com.curso.spring.rest.model.services.ClienteService;
import com.curso.spring.rest.model.services.ErrorService;
import com.curso.spring.rest.model.services.FacturaService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FacturaManager implements FacturaService {

    private final FacturaDao facturaDao;
    private final ClienteService clienteService;
    private final ErrorService errorService;
    private final static Logger LOG = LoggerFactory.getLogger(FacturaManager.class);

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
    public ResponseEntity<?> create(Integer id, Factura factura, BindingResult bindingResult) {
        Cliente cli;
        Map<String, Object> response = new HashMap<>();

        if (bindingResult.hasErrors()) {
            return errorService.throwErrors(bindingResult, response);
        }

        try {
            cli = this.clienteService.findById(id);
        } catch (DataAccessException e) {
            return this.errorService.dbError(e, response);
        }

        if (cli == null) {
            response.put("error", "El cliente " + id + " no existe");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        save(factura);
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

    @Override
    public ResponseEntity<?> exportPdf(Integer facturaId) {
        Factura factura = findById(facturaId);
        byte[] pdf = generarPdf(factura);
        HttpHeaders headers = new HttpHeaders();
        String nombre = "factura_" + facturaId;

        headers.add("Access-Control-Expose-Headers", "content-disposition");
        headers.add("content-disposition", "inline; filename=" + URLEncoder.encode(nombre));
        headers.add("Content-Type", "application/pdf");

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }

    private byte[] generarPdf(Factura factura) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);

            document.open();
            Font font = FontFactory.getFont(FontFactory.HELVETICA, Font.DEFAULTSIZE, BaseColor.BLACK);
            Chunk chunk = new Chunk("Factura: " + factura.getId(), font);
            document.add(chunk);

            document.close();
        } catch (DocumentException e) {
            LOG.error("Error al generar el documento", e);
        }

        return out.toByteArray();
    }

}
