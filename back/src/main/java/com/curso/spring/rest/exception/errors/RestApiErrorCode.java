package com.curso.spring.rest.exception.errors;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum RestApiErrorCode {

    CLIENTE_CON_FACTURAS(1, "El cliente tiene facturas"),

    PRODUCTO_EXISTE_EN_FACTURA(2, "Hay facturas que tienen este producto, no se puede eliminar"),

    PRODUCTO_NOMBRE_DUPLICADO(3, "Ya existe un producto con el mismo nombre"),

    CLIENTE_NOT_FOUND(4, "El cliente no existe")

    ;

    private final Integer value;
    private final String message;

    RestApiErrorCode(Integer value, String message) {
        this.value = value;
        this.message = message;
    }

    public Integer getValue() {
        return this.value;
    }

    public String getMessage() {
        return this.message;
    }

    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        return Enum.valueOf(enumType, name);
    }
}
