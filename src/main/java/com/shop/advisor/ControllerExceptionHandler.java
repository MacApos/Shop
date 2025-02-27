package com.shop.advisor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException() {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(notFound).body(Map.of("message", notFound.toString()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException() {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(unauthorized).body(Map.of("message", unauthorized.toString()));
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthorizationDeniedException() {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(forbidden).body(Map.of("message", forbidden.toString()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Map<String, Object> violations = constraintViolations
                .stream()
                .collect(Collectors.toMap(
                                v -> v.getPropertyPath().toString(),
                                ConstraintViolation::getMessage,
                                this::remappingFunction
                        )
                );
        return ResponseEntity.badRequest().body(violations);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        HashMap<String, Object> errors = new HashMap<>();
        bindingResult.getGlobalErrors().forEach(error -> errors.put(
                error.getObjectName(), error.getDefaultMessage())
        );

        bindingResult.getFieldErrors().forEach(error -> errors.merge(
                error.getField(), error.getDefaultMessage(), this::remappingFunction));
        return ResponseEntity.badRequest().body(errors);
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
