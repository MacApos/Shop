package com.shop.validation.validator;

import com.shop.validation.annotation.UserAlreadyEnabled;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserAlreadyEnabledValidator implements ConstraintValidator<UserAlreadyEnabled, Boolean> {

    @Override
    public boolean isValid(Boolean enabled, ConstraintValidatorContext constraintValidatorContext) {
        boolean b = !enabled;
        return b;
    }
}