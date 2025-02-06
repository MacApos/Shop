package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.repository.RegistrationTokenRepository;
import jakarta.validation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService {
    private final RegistrationTokenRepository registrationTokenRepository;
    private final LocalValidatorFactoryBean validatorFactory;

    public RegistrationToken generateAndSaveToken(User user) {
        RegistrationToken token = new RegistrationToken(user);
        token.setToken();
        token.setExpiryDate();
        save(token);
        return token;
    }

    public <T> void validateEntityWithLocalValidator(T entity) throws BindException {
        String simpleName = entity.getClass().getSimpleName();
        simpleName = Character.toUpperCase(simpleName.charAt(0)) + simpleName.substring(1);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(entity, simpleName);
        validatorFactory.validate(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
    }

    public <T> void validateEntity(T entity, Class<?>... groups) {
        Validator validator;
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity, groups);
            if(!constraintViolations.isEmpty()){
                throw new ConstraintViolationException(constraintViolations);
            }
        }
    }

    public RegistrationToken validateToken(RegistrationToken token) throws BindException {
        RegistrationToken existingToken = findByToken(token.getToken());
        validateEntityWithLocalValidator(existingToken);
        return existingToken;
    }

    public RegistrationToken findByToken(String token) {
        return registrationTokenRepository.findByToken(token);
    }

    public boolean existsByToken(String token) {
        return registrationTokenRepository.existsByToken(token);
    }

    public void save(RegistrationToken token) {
        registrationTokenRepository.save(token);
    }
}