package com.shop.service;

import com.shop.entity.Category;
import com.shop.interceptor.ServiceInterface;
import com.shop.repository.RoleRepository;
import com.shop.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shop.entity.User;

@Service
@RequiredArgsConstructor
public class UserService implements ServiceInterface<User> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public User findByUsername(String username) {
//        if (user != null) {
//            setRoles(user);
//        }
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsernameOrEmail(String username,String email) {
        return userRepository.existsByUsernameOrEmail(username, email);
    }

    @Transactional
    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        entityManager.persist(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }


//    private void setRoles(User user) {
//        Set<Role> authorities = roleRepository.findByUser(user);
//        if (authorities != null) {
//            user.setAuthorities(authorities);
//        }
//    }
//
//    public boolean isUserAdmin(String user) {
//        return findByUsername(user).getAuthorities()
//                .contains(roleRepository.findByName("ROLE_ADMIN"));
//    }
}
