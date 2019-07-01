package com.curso.spring.rest.models.manager;

import com.curso.spring.rest.models.services.ErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ErrorManager implements ErrorService {

    @Override
    public ResponseEntity<?> throwErrors(BindingResult result, Map<String, Object> response) {
        List<String> errors = result.getFieldErrors()
                .stream()
                .map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
                .collect(Collectors.toList());
        response.put("errores", errors);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
