package pl.coderslab.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.entity.User;
import pl.coderslab.repository.RoleRepository;
import pl.coderslab.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
//        if (user != null) {
//            setRoles(user);
//        }
        return user;
    }

//    private void setRoles(User user) {
//        Set<Role> authorities = roleRepository.findByUser(user);
//        if (authorities != null) {
//            user.setAuthorities(authorities);
//        }
//    }

    @Transactional
    public void save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        entityManager.persist(user);
    }

//
//    public boolean isUserAdmin(String user) {
//        return findByUsername(user).getAuthorities()
//                .contains(roleRepository.findByName("ROLE_ADMIN"));
//    }
}
