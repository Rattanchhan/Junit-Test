package com.spring_boot.caching.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class ApiException {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> handleResponseStatusException(final ResponseStatusException e) {
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .code(e.getStatusCode().value())
                .reason(e.getReason())
                .build();
        return ResponseEntity
                .status(e.getStatusCode())
                .body(Map.of("error",errorResponse));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<?> handleMethodArgumentNotValidException(final MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = new ArrayList<>();
        e.getFieldErrors().forEach(fieldError -> fieldErrors.add(FieldError.builder()
                .field(fieldError.getField())
                .detail(fieldError.getDefaultMessage()).build()));
        return ResponseEntity.status(e.getStatusCode().value())
                .body(Map.of("errors",
                        ErrorValidateResponse.builder()
                                .code(e.getStatusCode().value())
                                .reason(fieldErrors).build()));
    }
}
