package com.shop.validator;

import com.shop.entity.RegistrationToken;
import com.shop.service.RegistrationTokenService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenExistsValidator implements ConstraintValidator<TokenExists, String> {
    private final RegistrationTokenService registrationTokenService;

    @Override
    public boolean isValid(String registrationToken, ConstraintValidatorContext constraintValidatorContext) {
        if (registrationToken == null) {
            return false;
        }
        RegistrationToken existingToken = registrationTokenService.findByToken(registrationToken);
        if (existingToken == null) {
            return false;
        }
        return true;
    }
}
