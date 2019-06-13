package com.curso.spring.rest.models.manager;

import com.curso.spring.rest.models.dao.FacturaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FacturaManager {

    private final FacturaDao facturaDao;

    @Autowired
    public FacturaManager(FacturaDao facturaDao) {
        this.facturaDao = facturaDao;
    }
}
