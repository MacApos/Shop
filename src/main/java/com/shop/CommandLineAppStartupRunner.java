package com.shop;

import com.shop.entity.Role;
import com.shop.entity.User;
import com.shop.service.RoleService;
import com.shop.service.UserService;
import com.shop.validator.impl.UniqueValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CommandLineAppStartupRunner {
    private final UserService userService;
    private final RoleService roleService;

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        String roleUser = "ROLE_USER";
        String roleAdmin = "ROLE_ADMIN";
        List<User> users = List.of(
                new User("admin", "Adamin", "Nowak", "admin", "admin@gmail.com"),
                new User("user", "Andrzej", "Userski", "user", "user@gmail.com")
        );
        List<List<String>> roles = List.of(List.of(roleUser, roleAdmin), List.of(roleUser));

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
//            user.setEnabled(true);
//            userService.save(user);
//            roles.get(i).forEach(r -> roleService.save(new Role(r, user)));
        };
    }


}
