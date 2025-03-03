package com.shop.service;

import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Service;

@Service
public class ValidatorService {

    private ConstraintValidatorContext.ConstraintViolationBuilder builder(ConstraintValidatorContext context, String template) {
        context.disableDefaultConstraintViolation();
        return context.buildConstraintViolationWithTemplate(template);
    }

    public void addConstraint(ConstraintValidatorContext context, String template) {
        builder(context, template)
                .addConstraintViolation();
    }

    public void addConstraint(ConstraintValidatorContext context, String template, String node) {
        builder(context, template)
                .addPropertyNode(node)
                .addConstraintViolation();
    }
}
