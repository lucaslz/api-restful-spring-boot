package com.example.carros.api.exception;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.Serializable;

@RestControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            EmptyResultDataAccessException.class
    })
    public ResponseEntity<?> errorNotFound(Exception ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler({
            IllegalArgumentException.class
    })
    public ResponseEntity<?> errorBadRequest(Exception ex) {
        return ResponseEntity.badRequest().build();
    }
    
    @ExceptionHandler({
    	AccessDeniedException.class
    })
    public ResponseEntity<?> accessDanied() {
    	return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ExceptionError("Acesso Negado"));
    }
}

class ExceptionError implements Serializable {
	private static final long serialVersionUID = 1L;
	private String error;
    ExceptionError(String error) {
        this.error = error;
    }
    public String getError() {
        return error;
    }
}