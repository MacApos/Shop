package com.shop.validation.global.validator;

import com.shop.validation.global.annotation.MinSize;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MinSizeValidator implements ConstraintValidator<MinSize, String> {
    private int min;
    @Override
    public void initialize(MinSize constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
         min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.length() >= min;
    }
}
