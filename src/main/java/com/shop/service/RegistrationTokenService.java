package com.shop.service;

import com.shop.model.RegistrationToken;
import com.shop.model.User;
import com.shop.repository.RegistrationTokenRepository;
import com.shop.validation.user.group.sequence.ValidTokenSequence;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService extends AbstractService<RegistrationToken> {
    private final RegistrationTokenRepository registrationTokenRepository;
    private final LocalValidatorFactoryBean validatorFactory;
    private final EntityManager entityManager;

    @Transactional
    public RegistrationToken generateAndSaveToken(User user) {
        RegistrationToken token = registrationTokenRepository.findByUser(user);
        if (token == null) {
            token = new RegistrationToken(user);
        }
        token.setToken();
        token.setExpiryDate();
        entityManager.persist(token);
        entityManager.flush();
        return token;
    }

    @Transactional
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

    @Transactional
    public void save(RegistrationToken token) {
        entityManager.persist(token);
        entityManager.flush();
//        registrationTokenRepository.save(token);
    }

    public void delete(RegistrationToken token) {
        registrationTokenRepository.delete(token);
    }
}