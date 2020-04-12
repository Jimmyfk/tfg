package com.curso.spring.rest.exception;


public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 5658129865837267458L;

    private RestApiErrorCode error;

    public CustomException(RestApiErrorCode error) {
        super(error.getMessage());
        this.error = error;
    }

    public RestApiErrorCode getError() {
        return error;
    }
}
