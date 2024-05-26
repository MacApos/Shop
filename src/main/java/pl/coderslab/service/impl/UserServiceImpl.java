package pl.coderslab.service.impl;

import org.springframework.stereotype.Service;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;
import pl.coderslab.service.AuthorityService;
import pl.coderslab.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AuthorityService authorityService;

    public UserServiceImpl(UserRepository userRepository, AuthorityService authorityService) {
        this.userRepository = userRepository;
        this.authorityService = authorityService;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean isUserAdmin(String user) {
        return findByUsername(user).getAuthorities()
                .contains(authorityService.findByAuthority("ROLE_ADMIN"));
    }
}
