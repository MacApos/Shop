package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.repository.RegistrationTokenRepository;
import jakarta.persistence.EntityManager;
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
        RegistrationToken token = registrationTokenRepository.findByUser(user);
        if (token == null) {
            token = new RegistrationToken(user);
        }
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
            if (!constraintViolations.isEmpty()) {
                throw new ConstraintViolationException(constraintViolations);
            }
        }
    }

    private final EntityManager entityManager;
    public RegistrationToken validateToken(RegistrationToken token) throws BindException {
        RegistrationToken existingToken = findByToken(token.getToken());
        validateEntityWithLocalValidator(existingToken);
        existingToken.setAvailable(false);
        save(existingToken);
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