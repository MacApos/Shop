package com.shop.validation.global.validator;

import com.shop.validation.global.annotation.MinSize;
import com.shop.validation.user.annotation.NotNullEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinSizeValidatorEmail implements ConstraintValidator<NotNullEmail, String> {
    private int min;
    @Override
    public void initialize(NotNullEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
         min = 3;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.length() >= min;
    }
}
