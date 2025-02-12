package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import com.shop.mapper.UserMapper;
import com.shop.repository.RegistrationTokenRepository;
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
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(entity, simpleName);
        validatorFactory.validate(entity, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }
    }

//    public <T> void validateEntity(T entity, Class<?>... groups) {
//        if (groups.length == 0) {
//            groups = new Class[]{Default.class};
//        }
//        Set<ConstraintViolation<T>> constraintViolations = validatorFactory.validate(entity, groups);
//        if (!constraintViolations.isEmpty()) {
//            throw new ConstraintViolationException(constraintViolations);
//        }
//    }

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