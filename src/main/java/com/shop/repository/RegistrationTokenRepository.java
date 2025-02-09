package com.shop.repository;

import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends BaseRepository<RegistrationToken, Long> {
    RegistrationToken findByToken(String token);

    RegistrationToken findByUser(User user);

    boolean existsByToken(String token);
}