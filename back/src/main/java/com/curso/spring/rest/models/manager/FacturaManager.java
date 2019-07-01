package com.curso.spring.rest.models.manager;

import com.curso.spring.rest.models.dao.FacturaDao;
import com.curso.spring.rest.models.entity.Cliente;
import com.curso.spring.rest.models.entity.Factura;
import com.curso.spring.rest.models.services.FacturaService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaManager implements FacturaService {

    private final FacturaDao facturaDao;

    @Autowired
    public FacturaManager(FacturaDao facturaDao) {
        this.facturaDao = facturaDao;
    }

    @Override
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id) {
        return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }

    @Override
    public Page<Factura> findAllFacturasByClienteOrderById(Cliente cliente, Pageable pageable) {
        return facturaDao.findAllFacturasByClienteOrderById(cliente, pageable);
    }

    @Override
    public List<Factura> findAllByCliente(Cliente cliente) {
        return facturaDao.findAllByCliente(cliente);
    }

    @Override
    public Factura save(Factura factura) {
        return facturaDao.save(factura);
    }
}
