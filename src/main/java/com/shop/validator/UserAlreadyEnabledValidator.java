package com.shop.validator;

import com.shop.validator.annotations.UserAlreadyEnabled;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserAlreadyEnabledValidator implements ConstraintValidator<UserAlreadyEnabled, Boolean> {

    @Override
    public boolean isValid(Boolean enabled, ConstraintValidatorContext constraintValidatorContext) {
        return !enabled;
    }
}
