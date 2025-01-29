package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.repository.RegistrationTokenRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService {
    private final RegistrationTokenRepository registrationtokenRepository;
    private final EntityManager entityManager;
    private final LocalValidatorFactoryBean validatorFactory;

    public void validateToken(String token) throws BindException {
        RegistrationToken registrationToken = findByToken(token);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(registrationToken, "registrationToken");
        validatorFactory.validate(registrationToken, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
    }

    public RegistrationToken findByToken(String token) {
        return registrationtokenRepository.findByToken(token);
    }

    @Transactional
    public void save(RegistrationToken token) {
//        token.setToken();
//        token.setExpiryDate();
        token.setToken("test");
        token.setExpiryDate(LocalDateTime.now().plusSeconds(3));

        entityManager.persist(token);
        registrationtokenRepository.save(token);
    }
}