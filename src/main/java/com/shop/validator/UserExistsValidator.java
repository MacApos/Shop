package com.shop.validator;

import com.shop.entity.User;
import com.shop.service.UserService;
import com.shop.validator.annotations.UserExists;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExistsValidator implements ConstraintValidator<UserExists, User> {
    private final UserService userService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        if (user == null) {
            return false;
        }
        String email = user.getEmail();
        if(email == null){
            return false;
        }
        User existingUser = userService.findByEmail(email);
        return existingUser != null;
    }
}
