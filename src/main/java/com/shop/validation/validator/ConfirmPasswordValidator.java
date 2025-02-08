package com.shop.validation.validator;

import com.shop.entity.Password;
import com.shop.entity.User;
import com.shop.validation.annotation.ConfirmPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class ConfirmPasswordValidator implements ConstraintValidator <ConfirmPassword, User> {
    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        return user.getPassword().equals(user.getPasswordConfirm());
    }
}
