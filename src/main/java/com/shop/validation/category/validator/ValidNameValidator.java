package com.shop.validation.category.validator;

import com.shop.validation.category.annotation.ValidName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidNameValidator implements ConstraintValidator<ValidName, String> {
    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        if (name == null) {
            return false;
        }
        return name.chars().allMatch(c -> Character.isLetterOrDigit(c) || Character.isWhitespace(c));
    }
}
