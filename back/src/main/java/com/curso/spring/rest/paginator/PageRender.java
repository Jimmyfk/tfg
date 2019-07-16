package com.curso.spring.rest.paginator;

import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class PageRender<T> {

    private String url;
    private Page<T> page;

    private int total;
    private int paginaActual;

    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        int numElementos = page.getSize();
        this.total = page.getTotalPages();
        this.paginaActual = page.getNumber() + 1;
        this.paginas = new ArrayList<>();

        int desde, hasta;
        if (total <= numElementos) {
            desde = 1;
            hasta = total;
        } else {
            if (paginaActual <= numElementos / 2) {
                desde = 1;
                hasta = numElementos;
            } else if (paginaActual >= total - numElementos / 2) {
                desde = total - numElementos + 1;
                hasta = numElementos;
            } else {
                desde = paginaActual - numElementos / 2;
                hasta = numElementos;
            }
        }

        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde + i, paginaActual == desde + i));
        }
    }

    public String getUrl() {
        return url;
    }

    public int getTotal() {
        return total;
    }

    public int getPaginaActual() {
        return paginaActual;
    }

    public List<PageItem> getPaginas() {
        return paginas;
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean hasNext() {
        return page.hasNext();
    }

    public boolean hasPrevious() {
        return page.hasPrevious();
    }
}
