package com.shop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(UserService.class)
//@ContextConfiguration(classes = RepositoryConfiguration.class)
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void name() {

    }
}