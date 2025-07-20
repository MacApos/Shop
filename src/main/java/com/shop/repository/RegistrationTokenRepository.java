package com.shop.repository;

import com.shop.model.RegistrationToken;
import com.shop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends JpaRepository<RegistrationToken, Long> {
    RegistrationToken findByToken(String token);

    RegistrationToken findByUser(User user);

    boolean existsByToken(String token);
}