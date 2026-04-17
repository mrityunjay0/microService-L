package com.microServices.hotel.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> notFoundExceptionHandler(ResourceNotFoundException ex) {
        Map<String, Object> response = Map.of(
                "message", ex.getMessage(),
                "status", 404,
                "success", false
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
