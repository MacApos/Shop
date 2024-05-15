package pl.coderslab.service;

import pl.coderslab.entity.User;

public interface UserService {
    User findByUsername(String username);

    void save(User user);
}
