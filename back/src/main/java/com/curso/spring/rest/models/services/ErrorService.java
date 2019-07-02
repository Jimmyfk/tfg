package com.curso.spring.rest.models.services;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Service
public interface ErrorService {

    ResponseEntity<?> throwErrors(BindingResult result, Map<String, Object> response);

    ResponseEntity<?> dbError(DataAccessException e, Map<String, Object> response);
}
