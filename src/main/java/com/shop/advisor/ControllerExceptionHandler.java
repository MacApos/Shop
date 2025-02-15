package com.shop.advisor;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, List<String>>> handleValidationExceptions(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Map<String, List<String>> errors = constraintViolations.stream()
                .collect(Collectors.groupingBy(
                        violation -> violation.getPropertyPath().toString(),
                        Collectors.mapping(ConstraintViolation::getMessage, Collectors.toList()))
                );
        return ResponseEntity.badRequest().body(errors);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String, List<String>> fieldErrors = bindingResult.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));
        Map<String, Object> errors = new HashMap<>(fieldErrors);

        List<ObjectError> globalErrorsList = bindingResult.getGlobalErrors();
        if (!globalErrorsList.isEmpty()) {
            List<String> globalErrors = globalErrorsList
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            errors.put("globalErrors", globalErrors);
        }

        return ResponseEntity.badRequest().body(errors);
    }
}
