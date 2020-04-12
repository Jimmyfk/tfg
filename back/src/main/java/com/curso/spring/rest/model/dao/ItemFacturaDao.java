package com.curso.spring.rest.model.dao;

import com.curso.spring.rest.model.entity.ItemFactura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemFacturaDao extends JpaRepository<ItemFactura, Long> {

    Long countAllByProductoId(Integer productoId);
}
