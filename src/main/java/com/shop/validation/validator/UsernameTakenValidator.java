package com.shop.validation.validator;

import com.shop.service.UserService;
import com.shop.validation.annotation.UsernameTaken;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameTakenValidator implements ConstraintValidator<UsernameTaken, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if(username==null){
            return false;
        }
        return !userService.existsByUsername(username);
    }
}
