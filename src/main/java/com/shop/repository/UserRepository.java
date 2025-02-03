package com.shop.repository;

import org.springframework.stereotype.Repository;
import com.shop.entity.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);
}

