package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.User;
import com.shop.interceptor.InterceptorConfig;
import com.shop.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private HomeController controller;

    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }


//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private UserService userService;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private User user;
//
//    @BeforeAll
//    public void ini() {
//        user = new User("johnDoe",
//                "John",
//                "Doe",
//                "Password123",
//                "john.doe@gmail.com");
//    }
//
//    @Test
//    void givenUser_whenPostRequestToUserCreate_thenCorrectResponse() throws Exception {
//        String s = objectMapper.writeValueAsString(user);
////        mockMvc.perform(post("/user/create")
////                        .content(s)
////                        .contentType(MediaType.APPLICATION_JSON))
////                .andExpect(status().isOk());
//    }
//
//    @Test
//    void givenUserWithTooShortUsername_whenPostRequestToUserCreate_thenCorrectResponse() {
//        user.setUsername("JD");
//
//    }

}
