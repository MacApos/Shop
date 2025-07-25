package com.shop.validation.user.validator;

import com.shop.model.User;
import com.shop.service.ValidatorService;
import com.shop.validation.user.annotation.ConfirmPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, User> {
    private final ValidatorService validatorService;

    @Override
    public boolean isValid(User user, ConstraintValidatorContext constraintValidatorContext) {
        String passwordConfirm = user.getPasswordConfirm();
        if (passwordConfirm == null || !passwordConfirm.equals(user.getPassword())) {
            String messageCode = passwordConfirm == null ?
                    "{jakarta.validation.constraints.NotNull.message}" :
                    "{invalid.password.confirmation}";
            validatorService.addConstraint(constraintValidatorContext, messageCode,
                    "passwordConfirm");
            return false;
        }
        return true;
    }
}
