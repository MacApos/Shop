package com.shop.repository;

import com.shop.entity.RegistrationToken;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends BaseRepository<RegistrationToken, Long> {
    RegistrationToken findByToken(String token);

    boolean existsByToken(String token);
}