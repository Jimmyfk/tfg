package com.curso.spring.rest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RestApiErrorCode {

    CLIENTE_CON_FACTURAS(1, "El cliente tiene facturas"),

    PRODUCTO_EXISTE_EN_FACTURA(2, "Hay facturas que tienen este producto, no se puede eliminar"),

    PRODUCTO_NOMBRE_DUPLICADO(3, "Ya existe un producto con el mismo nombre")

    ;

    private final Integer value;
    private final String error;

    RestApiErrorCode(Integer value, String error) {
        this.value = value;
        this.error = error;
    }

    public Integer getValue() {
        return value;
    }

    public String getError() {
        return error;
    }

    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        return Enum.valueOf(enumType, name);
    }
}
