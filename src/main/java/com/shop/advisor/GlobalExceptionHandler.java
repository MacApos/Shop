package com.shop.advisor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException() {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(notFound).body(Map.of("message", notFound.toString()));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException() {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(unauthorized).body(Map.of("message", unauthorized.toString()));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthorizationDeniedException() {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(forbidden).body(Map.of("message", forbidden.toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, ArrayList<String>>> handleValidationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        HashMap<String, ArrayList<String>> violations = new HashMap<>();
        constraintViolations.forEach(violation ->
                computeIfAbsent(violations, violation.getPropertyPath().toString(), violation.getMessage())
        );
        return ResponseEntity.badRequest().body(violations);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, ArrayList<String>>> handleValidationExceptions(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        HashMap<String, ArrayList<String>> errors = new HashMap<>();
        bindingResult.getGlobalErrors().forEach(error ->
                computeIfAbsent(errors, error.getObjectName(), error.getDefaultMessage())
        );
        bindingResult.getFieldErrors().forEach(error ->
                computeIfAbsent(errors, error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.badRequest().body(errors);
    }

    private void computeIfAbsent(Map<String, ArrayList<String>> map, String key, String value) {
        map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
    }

    private Object remappingFunction(Object existingValue, Object newValue) {
        if (existingValue instanceof String) {
            return new ArrayList<>(List.of(existingValue, newValue));
        }
        ArrayList<Object> list = (ArrayList<Object>) existingValue;
        list.add(newValue);
        return list;
    }
}
