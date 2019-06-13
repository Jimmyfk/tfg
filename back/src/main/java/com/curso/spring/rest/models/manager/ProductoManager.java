package com.curso.spring.rest.models.manager;

import com.curso.spring.rest.models.dao.ProductoDao;
import com.curso.spring.rest.models.entity.Producto;
import com.curso.spring.rest.models.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductoManager implements ProductoService {

    private final ProductoDao productoDao;

    @Autowired
    public ProductoManager(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }


    @Override
    public List<Producto> findByNombreLikeIgnoreCase(String nombre) {
        return productoDao.findByNombreLikeIgnoreCase(nombre);
    }
}
