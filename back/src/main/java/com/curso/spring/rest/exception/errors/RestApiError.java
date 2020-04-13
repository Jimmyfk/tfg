package com.curso.spring.rest.exception.errors;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestApiError {

    private Long timestamp;
    private HttpStatus httpStatus;
    private RestApiErrorCode code;
    private String customMessage;

    public RestApiError() {
        timestamp = Instant.now().toEpochMilli();
    }

    public RestApiError(RestApiErrorCode code) {
        this();
        this.code = code;
    }

    public RestApiError(HttpStatus httpStatus, RestApiErrorCode code) {
        this(code);
        this.httpStatus = httpStatus;
    }

    public RestApiError(HttpStatus httpStatus, RestApiErrorCode code, String customMessage) {
        this(httpStatus, code);
        this.customMessage = customMessage;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public RestApiErrorCode getCode() {
        return code;
    }

    public void setCode(RestApiErrorCode code) {
        this.code = code;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }
}
