package com.shop.validation.user.validator;

import com.shop.entity.User;
import com.shop.service.MessageService;
import com.shop.service.UserService;
import com.shop.validation.user.annotation.UserAlreadyExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class UserAlreadyExistValidator implements ConstraintValidator<UserAlreadyExist, User> {
    private final UserService userService;
    private final MessageService messageService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        boolean usernameExists = userService.existsByUsername(user.getUsername());
        boolean emailExists = userService.existsByEmail(user.getEmail());

        if(!usernameExists && !emailExists){
            return true;
        }

        HashMap<String, String> map = new HashMap<>();
        if (usernameExists) {
            map.put("username", messageService.getMessage("user.username.already.exists"));
        }

        if (emailExists) {
            map.put("email", messageService.getMessage("user.email.already.exists"));
        }

        constraintValidatorContext.disableDefaultConstraintViolation();
        map.forEach((k, v) ->
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate(v)
                    .addPropertyNode(k)
                    .addConstraintViolation()
        );
        return false;
    }
}
