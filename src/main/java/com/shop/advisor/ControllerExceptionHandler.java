package com.shop.advisor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.*;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException() {
        HttpStatus notFound = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(notFound).body(Map.of("message", notFound.toString()));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException() {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(unauthorized).body(Map.of("message", unauthorized.toString()));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAuthorizationDeniedException() {
        HttpStatus forbidden = HttpStatus.FORBIDDEN;
        return ResponseEntity.status(forbidden).body(Map.of("message", forbidden.toString()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Map<String, List<String>> violations = constraintViolations
                .stream()
                .collect(Collectors.groupingBy(
                        v -> v.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList()))
                );

        HashMap<String, Object> violationsMap = new HashMap<>();
        constraintViolations.forEach(violation -> violationsToMap(violationsMap, violation.getPropertyPath().toString(), violation.getMessage()));
        for (ConstraintViolation<?> violation : constraintViolations) {
            violationsToMap(violationsMap, violation.getPropertyPath().toString(), violation.getMessage());
        }

        return ResponseEntity.badRequest().body(violations);
    }

    private void violationsToMap(Map<String, Object> map, String key, String value) {
        map.merge(key, value, (existingValue, newValue) -> {
            if (existingValue instanceof String) {
                return new ArrayList<>(List.of(existingValue, newValue));
            }
            ArrayList<Object> list = (ArrayList<Object>) existingValue;
            list.add(newValue);
            return list;
        });
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        Map<String, List<String>> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
        return ResponseEntity.badRequest().body(fieldErrors);
    }
}
