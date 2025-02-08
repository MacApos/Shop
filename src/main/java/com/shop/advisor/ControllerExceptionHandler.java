package com.shop.advisor;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(BindException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<FieldError> fieldErrors1 = bindingResult.getFieldErrors();
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
