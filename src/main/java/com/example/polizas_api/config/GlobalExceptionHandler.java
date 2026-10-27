package com.example.polizas_api.config;

import com.example.polizas_api.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> notFound(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorResponse.builder()
                        .status(404)
                        .error("Not Found")
                        .mensaje(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> conflict(IllegalStateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ErrorResponse.builder()
                        .status(409)
                        .error("Conflict")
                        .mensaje(ex.getMessage())
                        .build());
    }
}
