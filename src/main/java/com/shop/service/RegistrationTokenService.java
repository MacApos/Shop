package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.repository.RegistrationTokenRepository;
import com.shop.validation.annotation.ValidToken;
import com.shop.validation.group.sequence.ValidTokenSequence;
import jakarta.validation.groups.Default;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindException;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService extends AbstractService<RegistrationToken>
        implements ServiceInterface<RegistrationToken> {
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
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(entity,  simpleName);
        validatorFactory.validate(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
    }

    public RegistrationToken validateToken(RegistrationToken token) {
        RegistrationToken existingToken = findByToken(token.getToken());
        validate(existingToken, ValidTokenSequence.class);
        existingToken.setActive(false);
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