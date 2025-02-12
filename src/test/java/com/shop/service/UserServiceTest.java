package com.shop.service;

import com.shop.common.CategoriesInitiation;
import com.shop.entity.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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