package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

//@AutoConfigureWebMvc
@WebMvcTest(UserController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(UserService.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    ObjectMapper objectMapper;

    @BeforeAll
    public void ini() {
//        new User
        objectMapper = new ObjectMapper();
    }

    @Test
    void givenUserWithTooShortUsername_whenPostRequestToUserCreate_thenCorrectResponse() {

    }

}
