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
@RequiredArgsConstructor
public class UserService extends AbstractService<User> implements ServiceInterface<User> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final UserMapper userMapper;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByNewEmail(String newEmail) {
        return userRepository.findByEmail(newEmail);
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public boolean existsBy(String username) {
        return existsByUsername(username);
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

    public void update(User source, User target) {
        userMapper.update(source, target);
    }

    @Transactional
    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        entityManager.persist(user);
        entityManager.flush();
    }

    @Transactional
    public void delete(User user) {
        entityManager.remove(user);
        entityManager.flush();
//        userRepository.delete(user);
    }
}
