package com.shop.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.model.RegistrationToken;
import com.shop.model.User;
import com.shop.listener.ApplicationStartupListener;
import com.shop.listener.RegistrationEventListener;
import com.shop.service.JwtTokenService;
import com.shop.service.MessageService;
import com.shop.service.RegistrationTokenService;
import com.shop.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @Autowired
    private RegistrationTokenService registrationTokenService;

    @MockitoBean
    private ApplicationStartupListener startupListener;

    @MockitoBean
    private RegistrationEventListener registrationEventListener;

    @MockitoBean
    private JwtTokenService jwtTokenService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private User newUser;
    private User invalidUser;
    private User existingUser;
    private User userWithExpiredToken;
    private final String validToken = "validToken";
    private final String invalidToken = "invalidToken";
    private final String expiredToken = "expiredToken";

    @BeforeAll
    public void init() {
        newUser = new User("johnDoe", "John", "Doe",
                "P@ssword123", "P@ssword123", "john.doe@gmail.com");
        invalidUser = new User("JD", "John", "Doe",
                "P@ssword456", "P@ssword456", "j.d@gmail.com");
        existingUser = new User("janeDoe", "Jane", "Doe",
                "P@ssword789", "P@ssword789", "jane.doe@gmail.com");
        userWithExpiredToken = new User("fooBar", "Foo", "Bar",
                "FooBar|23", "FooBar|23", "foo.bar@gmail.com");
        userWithExpiredToken.setEnabled(true);

        saveUserAndToken(existingUser, validToken, null);
        saveUserAndToken(userWithExpiredToken, expiredToken, LocalDateTime.now().plusSeconds(1));
    }

    private String toJson(User user) {
        Map<String, String> map = Map.of("username", user.getUsername(),
                "firstname", user.getFirstname(),
                "lastname", user.getLastname(),
                "password", user.getPassword(),
                "passwordConfirm", user.getPasswordConfirm(),
                "email", user.getEmail());
        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void saveUserAndToken(User user, String tokenName, LocalDateTime expiryDate) {
        userService.save(user);
        RegistrationToken token = new RegistrationToken();
        token.setUser(user);
        token.setToken(tokenName);
        if (expiryDate == null) {
            token.setExpiryDate();
        } else {
            token.setExpiryDate(expiryDate);
        }
        registrationTokenService.save(token);
    }

    public MockHttpServletRequestBuilder postRequestBuilder(String uriTemplate, String content) {
        return post(uriTemplate)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder getRequestBuilder(String uriTemplate, String paramName, String paramValue) {
        return get(uriTemplate)
                .param(paramName, paramValue)
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void givenUser_whenPostRequestToUserCreate_thenStatusIsOk() throws Exception {
        mockMvc.perform(postRequestBuilder("/user/create", toJson(newUser)))
                .andExpect(status().isOk());
    }

    @Test
    void givenUserWithTooShortUsername_whenPostRequestToUserCreate_thenBadRequest() throws Exception {
        String json = toJson(invalidUser);
        String message = messageService.getMessage("jakarta.validation.constraints.Size.message");
        message = message.replace("{min}", "3");

        mockMvc.perform(postRequestBuilder("/user/create", json))
                .andExpect(jsonPath("$.username").value(message));
    }

    @Test
    void givenUserWithTooShortUsername_whenInternationalizedPostRequestToUserCreate_thenBadRequest() throws Exception {
        String json = toJson(invalidUser);
        Locale locale = new Locale("PL", "pl");
        String message = messageService.getMessage("jakarta.validation.constraints.Size.message", locale);
        message = message.replace("{min}", "3");

        mockMvc.perform(postRequestBuilder("/user/create", json).locale(locale))
                .andExpect(jsonPath("$.username").value(message));
    }

    @Test
    void givenUserWithTakenUsername_whenPostRequestToUserCreate_thenBadRequest() throws Exception {
        String message = messageService.getMessage("user.username.already.exists");
        mockMvc.perform(postRequestBuilder(
                        "/user/create",
                        toJson(existingUser)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.username").value(message));
    }

    @Test
    void givenValidToken_whenGetRequestToConfirmRegistration_thenStatusIsOk() throws Exception {
        mockMvc.perform(getRequestBuilder(
                        "/user/confirm-registration",
                        "token",
                        validToken))
                .andExpect(status().isOk());
        assertThat(registrationTokenService.findByToken(validToken))
                .isNotNull()
                .extracting(RegistrationToken::isActive)
                .isEqualTo(false);
    }

    @Test
    void givenInvalidToken_whenGetRequestToConfirmRegistration_thenBadRequest() throws Exception {
        String message = messageService.getMessage("does.not.exist");
        mockMvc.perform(getRequestBuilder(
                        "/user/confirm-registration",
                        "token",
                        invalidToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token").value(message));
    }

    @Test
    void givenUnavailableToken_whenGetRequestToResendRegistrationToken_thenBadRequest() throws Exception {
        String message = messageService.getMessage("does.not.exist");
        mockMvc.perform(getRequestBuilder(
                        "/user/resend-registration-token",
                        "token",
                        invalidToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token").value(message));
    }

    @Test
    void givenExpiredToken_whenGetRequestToConfirmRegistration_thenBadRequest() throws Exception {
        String message = messageService.getMessage("token.expired");
        mockMvc.perform(getRequestBuilder(
                        "/user/confirm-registration",
                        "token",
                        expiredToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.expiryDate").value(message));
    }

    @Test
    void givenInvalidToken_whenGetRequestToResendRegistrationToken_thenBadRequest() throws Exception {
        String message = messageService.getMessage("does.not.exist");
        mockMvc.perform(getRequestBuilder(
                        "/user/resend-registration-token",
                        "token",
                        invalidToken))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.token").value(message));
    }


}
