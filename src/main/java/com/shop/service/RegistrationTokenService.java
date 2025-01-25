package com.shop.service;

import com.shop.entity.RegistrationToken;
import com.shop.repository.RegistrationTokenRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RegistrationTokenService {
    private final RegistrationTokenRepository registrationtokenRepository;
    private final EntityManager entityManager;

    @Transactional
    public void save(RegistrationToken token){
        token.setToken();
        token.setExpiryDate();
        entityManager.persist(token);
        registrationtokenRepository.save(token);
    }
}