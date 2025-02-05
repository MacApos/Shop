package com.shop.validation.validator;

import com.shop.entity.User;
import com.shop.service.UserService;
import com.shop.validation.annotation.UserExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExistsValidator implements ConstraintValidator<UserExists, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if(email == null){
            return false;
        }
        return userService.existsByEmail(email);
    }
}
