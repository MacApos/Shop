package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.repository.RegistrationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService {
    private final RegistrationTokenRepository registrationtokenRepository;
    private final LocalValidatorFactoryBean validatorFactory;

    public RegistrationToken generateToken(User user) {
        RegistrationToken token = new RegistrationToken(user);
        token.setToken();
        token.setExpiryDate();
        save(token);
        return token;
    }

    public RegistrationToken validateToken(String token) throws BindException {
        RegistrationToken registrationToken = findByToken(token);
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(registrationToken,
                "registrationToken");
        validatorFactory.validate(registrationToken, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
        return registrationToken;
    }

    public RegistrationToken findByToken(String token) {
        return registrationtokenRepository.findByToken(token);
    }

    public void save(RegistrationToken token) {
        registrationtokenRepository.save(token);
    }
}