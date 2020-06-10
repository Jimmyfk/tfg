package com.curso.spring.rest.exception;


import com.curso.spring.rest.exception.errors.RestApiError;
import com.curso.spring.rest.exception.errors.RestApiErrorCode;
import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class CustomException extends RuntimeException implements Supplier<CustomException> {

    private static final long serialVersionUID = 5658129865837267458L;

    private final RestApiError error;

    public CustomException(RestApiErrorCode error) {
        super(error.getMessage());
        this.error = new RestApiError(error);
    }

    public CustomException(HttpStatus status, RestApiErrorCode error) {
        super(error.getMessage());
        this.error = new RestApiError(status, error);
    }

    public CustomException(HttpStatus status, RestApiErrorCode error, String customMessage) {
        super(error.getMessage());
        this.error = new RestApiError(status, error, customMessage);
    }

    public RestApiError getError() {
        return this.error;
    }

    @Override
    public CustomException get() {
        return this;
    }
}
