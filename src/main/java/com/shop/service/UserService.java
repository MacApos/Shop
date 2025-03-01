package com.shop.service;

import com.shop.mapper.UserMapper;
import com.shop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shop.entity.User;

@Service
public class UserService extends AbstractService<User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public UserService(EntityManager entityManager, UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, UserMapper userMapper1) {
        super.setMapper(userMapper);
        this.entityManager = entityManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByNewEmail(String newEmail) {
        return userRepository.findByEmail(newEmail);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }


    public boolean existsByUsernameOrEmail(User user) {
        return userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail());
    }

    @Transactional
    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setEnabled(true);
        entityManager.persist(user);
    }

    @Transactional
    public void delete(User user) {
        entityManager.remove(user);
    }
}
