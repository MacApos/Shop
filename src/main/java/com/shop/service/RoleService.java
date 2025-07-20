package com.shop.service;

import com.shop.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.shop.model.Role;
import com.shop.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findByUser(User user) {
        return roleRepository.findByUser(user);
    }

    public void save(Role role) {
        roleRepository.save(role);
    }
}
