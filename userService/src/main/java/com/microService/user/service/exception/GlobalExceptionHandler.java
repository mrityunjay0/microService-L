package com.microService.user.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException{

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponsePayload> handleResourceNotFoundException(ResourceNotFoundException ex) {

            ErrorResponsePayload errorResponse = new ErrorResponsePayload(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    false);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
