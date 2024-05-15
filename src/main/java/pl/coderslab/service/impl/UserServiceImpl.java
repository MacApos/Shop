package pl.coderslab.service.impl;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.coderslab.entity.User;
import pl.coderslab.repository.AuthorityRepository;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private  UserRepository userRepository;
    private  AuthorityRepository authorityRepository;
    private  BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {

    }
}
