package com.curso.spring.rest.model.manager;

import com.curso.spring.rest.model.services.ErrorService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
                .map(err -> !isList(err.getField()) ? "El campo '" + err.getField() + "' " + err.getDefaultMessage()
                        : err.getDefaultMessage())
                .collect(Collectors.toList());
        response.put("errores", errors);
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    private boolean isList(String field) {
        return field.equals("items");
    }

    @Override
    public ResponseEntity<?> dbError(DataAccessException e, Map<String, Object> response) {
        response.put("mensaje", "Error al realizar la consulta en la base de datos");
        response.put("error", !StringUtils.isEmpty(e.getMessage()) ? e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()) : false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
