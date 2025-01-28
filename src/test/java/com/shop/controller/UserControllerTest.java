package com.shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.listener.ApplicationStartupListener;
import com.shop.listener.RegistrationEventListener;
import com.shop.service.MessageService;
import com.shop.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageService messageSourceService;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private ApplicationStartupListener startupListener;

    @MockitoBean
    private RegistrationEventListener registrationEventListener;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> map = new HashMap<>();

    @BeforeAll
    public void ini() {
        map.put("username", "johnDoe");
        map.put("firstname", "John");
        map.put("lastname", "Doe");
        map.put("password", "Password123");
        map.put("email", "john.doe@gmail.com");
    }

    @Test
    void givenUser_whenPostRequestToUserCreate_thenCorrectResponse() throws Exception {
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        mockMvc.perform(post("/user/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void givenUserWithTooShortUsername_whenPostRequestToUserCreate_thenCorrectResponse() throws Exception {
        map.replace("username", "jd");
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        String message = messageSourceService.getMessage("jakarta.validation.constraints.Size.message");
        message = message.replace("{min}", "3");

        mockMvc.perform(post("/user/create")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value(message));
    }

    @Test
    void givenUserWithTooShortUsername_whenPostPLRequestToUserCreate_thenCorrectResponse() throws Exception {
        map.replace("username", "jd");
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        Locale locale = new Locale("PL", "pl");
        String message = messageSourceService.getMessage("jakarta.validation.constraints.Size.message", locale);
        message = message.replace("{min}", "3");

        mockMvc.perform(post("/user/create")
                        .header("Accept-Language", "pl")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value(message));
    }



}
