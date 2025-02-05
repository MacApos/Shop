package com.shop.validator;

import com.shop.entity.Password;
import com.shop.validation.annotation.ConfirmPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator <ConfirmPassword, Password> {
    @Override
    public boolean isValid(Password password, ConstraintValidatorContext constraintValidatorContext) {
        return password.getPassword().equals(password.getPasswordConfirm());
    }
}
