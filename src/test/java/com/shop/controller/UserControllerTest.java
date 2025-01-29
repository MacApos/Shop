package com.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.entity.User;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.lang.reflect.Field;
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

    @Autowired
    private UserService userService;

    @MockitoBean
    private ApplicationStartupListener startupListener;

    @MockitoBean
    private RegistrationEventListener registrationEventListener;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, String> map = new HashMap<>();
    private User user;
    private String json;

    @BeforeAll
    public void init() throws JsonProcessingException {
        map.put("username", "johnDoe");
        map.put("firstname", "John");
        map.put("lastname", "Doe");
        map.put("password", "Password123");
        map.put("email", "john.doe@gmail.com");
        json = mapToJson(map);
        user = objectMapper.readValue(json, User.class);
    }

    private String mapToJson(Map<String, String> map) {
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private MockHttpServletRequestBuilder requestBuilder(String uriTemplate, String content) {
        return post(uriTemplate)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);
    }

    private ResultActions performPostAndExceptBadRequest(String uriTemplate, String content) throws Exception {
        return mockMvc.perform(requestBuilder(uriTemplate, content))
                .andExpect(status().isBadRequest());
    }

    private void performPostAndExceptStatusIsOk(String uriTemplate, String content) throws Exception {
        mockMvc.perform(requestBuilder(uriTemplate, content))
                .andExpect(status().isOk());
    }

    @Test
    void givenUser_whenPostRequestToUserCreate_thenCorrectResponse() throws Exception {
        performPostAndExceptStatusIsOk("/user/create", json);

    }

    @Test
    void givenUserWithTooShortUsername_whenPostRequestToUserCreate_thenCorrectResponse() throws Exception {
        map.replace("username", "jd");
        String json = mapToJson(map);
        String message = messageSourceService.getMessage("jakarta.validation.constraints.Size.message");
        message = message.replace("{min}", "3");

        performPostAndExceptBadRequest("/user/create", json)
                .andExpect(jsonPath("$.username").value(message));
    }

    @Test
    void givenUserWithTooShortUsername_whenPostPLRequestToUserCreate_thenCorrectResponse() throws Exception {
        map.replace("username", "jd");
        String json = mapToJson(map);
        Locale locale = new Locale("PL", "pl");
        String message = messageSourceService.getMessage("jakarta.validation.constraints.Size.message", locale);
        message = message.replace("{min}", "3");

        mockMvc.perform(requestBuilder("/user/create", json)
                        .locale(locale))
                .andExpect(jsonPath("$.username").value(message));
    }

    @Test
    void givenUserWithTakenUsername_whenPostRequestToUserCreate_thenCorrectResponse() throws Exception {
        if (!userService.existsByUsername(user.getUsername())) {
            userService.save(user);
        }
        String message = messageSourceService.getMessage("user.username.already.exists");
        performPostAndExceptBadRequest("/user/create", json)
                .andExpect(jsonPath("$.username").value(message));
    }

}
