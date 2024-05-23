package pl.coderslab.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.coderslab.entity.CustomUser;
import pl.coderslab.entity.User;
import pl.coderslab.repository.UserRepository;

import java.util.HashSet;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        HashSet<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getAuthorities().forEach(a -> grantedAuthorities.add(new SimpleGrantedAuthority(a.getAuthority())));
        return new CustomUser(user.getUsername(), user.getPassword(),
                grantedAuthorities,user);
    }

}
