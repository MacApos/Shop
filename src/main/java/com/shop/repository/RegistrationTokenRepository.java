package com.shop.repository;

import com.shop.entity.Product;
import com.shop.entity.RegistrationToken;
import com.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationTokenRepository extends
        JpaRepository<RegistrationToken, Long>
//        BaseRepository<RegistrationToken, Long>
        {
    RegistrationToken findByToken(String token);

    RegistrationToken findByUser(User user);

    boolean existsByToken(String token);
}