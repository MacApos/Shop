package com.shop.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.service.MessageService;
import com.shop.service.UserService;
import com.shop.validation.group.ResetPassword;
import com.shop.validation.group.sequence.CreateSequence;
import com.shop.validation.group.sequence.UpdateEmailSequence;
import com.shop.validation.group.sequence.UpdateSequence;
import jakarta.validation.*;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestPropertySource("/application-test.properties")
class UserValidationTest {
    @Autowired
    private MessageService messageService;

    @Autowired
    private LocalValidatorFactoryBean factoryBean;

    @Autowired
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private User existingUser;
    private User newUser;

    private final String nullMessage = "jakarta.validation.constraints.NotNull.message";
    private final String invalidEmail = "jakarta.validation.constraints.Email.message";
    private final String invalidSize = "jakarta.validation.constraints.Size.message";
    private final String invalidPasswordConfirmation = "invalid.password.confirmation";

    @BeforeAll
    public void init() {
        existingUser = new User("johnDoe", "John", "Doe",
                "P@ssword123", "P@ssword123", "john.doe@gmail.com");
        newUser = new User("janeDoe", "Jane", "Doe",
                "P@ssword456", "P@ssword456", "jane.doe@gmail.com");
        User saveUser = copyUser(existingUser);
        saveUser.setEnabled(true);
        userService.save(saveUser);
    }

    private User copyUser(User user) {
        User result = objectMapper.convertValue(user, User.class);
        result.setPassword(user.getPassword());
        result.setPasswordConfirm(user.getPasswordConfirm());
        return result;
    }

    private void assertEqualViolationsWithMessages(Map<String, Object> expectedViolations, User user,
                                                   Class<?>... groups) {
        Set<ConstraintViolation<User>> violations = factoryBean.validate(user, groups);
        assertThat(violations).isNotEmpty();

        List<Tuple> tuples = expectedViolations.entrySet().stream().flatMap(e -> {
            Object key = e.getKey();
            Object value = e.getValue();
            if (value instanceof List<?> values) {
                return values.stream().map(v -> tuple(key, v));
            }
            return Stream.of(tuple(key, value));
        }).toList();

        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrderElementsOf(tuples);
    }

    private void assertEqualViolations(Map<String, Object> expectedViolations, User user, Class<?>... groups) {
        HashMap<String, Object> map = new HashMap<>(expectedViolations);
        map.replaceAll((k, v) -> messageService.getMessage((String) v));
        assertEqualViolationsWithMessages(map, user, groups);
    }

    @Test
    void givenUserWithNullValues_thenReturnConstraintValidationException() {
        User user = new User();
        Map<String, Object> expectedValidations = Map.of(
                "username", nullMessage,
                "firstname", nullMessage,
                "lastname", nullMessage,
                "password", nullMessage,
                "passwordConfirm", nullMessage,
                "email", nullMessage);
        assertEqualViolations(expectedValidations, user, CreateSequence.class);
    }

    @Test
    void givenUserWithInvalidData_thenReturnConstraintValidationException() {
        User user = copyUser(existingUser);
        user.setUsername("JD");
        user.setFirstname("Jo");
        user.setLastname("Do");
        String replace = messageService.getMessage(invalidSize).replace("{min}", "3");
        Map<String, Object> expectedViolations = Map.of(
                "username", replace,
                "firstname", replace,
                "lastname", replace
        );
        assertEqualViolationsWithMessages(expectedViolations, user, CreateSequence.class);
        assertEqualViolationsWithMessages(expectedViolations, user, UpdateSequence.class);
    }

    @Test
    void givenUserWithTakenUsernameAndEmail_thenReturnConstraintValidationException() {
        Map<String, Object> expectedValidations = new HashMap<>();
        expectedValidations.put("username", messageService.getMessage("user.username.already.exists"));
        assertEqualViolationsWithMessages(expectedValidations, existingUser, UpdateSequence.class);
        expectedValidations.put("email", messageService.getMessage("user.email.already.exists"));
        assertEqualViolationsWithMessages(expectedValidations, existingUser, CreateSequence.class);
    }

    @Test
    void givenUserWithInvalidEmail_thenReturnConstraintValidationException() {
        User user = copyUser(newUser);
        user.setEmail("johndoegmailcom");
        assertEqualViolations(Map.of("email", invalidEmail), user, CreateSequence.class);
    }

    @Test
    void givenUserWithNullPassword_thenReturnConstraintValidationException() {
        User user = copyUser(existingUser);
        user.setPassword(null);
        Map<String, Object> expectedValidations = Map.of(
                "password", nullMessage,
                "passwordConfirm", "invalid.password.confirmation");
        assertEqualViolations(expectedValidations, user, CreateSequence.class);
    }

    @Test
    void givenUserInvalidPassword_thenReturnConstraintValidationException() {
        User user = copyUser(newUser);
        user.setPassword("pass");
        Map<String, Object> expectedViolations = Map.of(
                "password", List.of(
                        messageService.getMessage("TOO_SHORT").replace("%1$s", "8"),
                        messageService.getMessage("INSUFFICIENT_SPECIAL").replace("%1$s", "1"),
                        messageService.getMessage("INSUFFICIENT_UPPERCASE").replace("%1$s", "1"),
                        messageService.getMessage("INSUFFICIENT_DIGIT").replace("%1$s", "1")),
                "passwordConfirm", messageService.getMessage("invalid.password.confirmation")
        );
        assertEqualViolationsWithMessages(expectedViolations, user, CreateSequence.class);
    }

    @Test
    void givenUserInvalidEmailAndNewEmail_thenReturnConstraintValidationException() {
        User user = new User();
        user.setEmail("johndoegmailcom");
        user.setNewEmail("johndoe2gmailcom");
        Map<String, Object> expectedValidations = Map.of(
                "email", invalidEmail,
                "newEmail", invalidEmail);
        assertEqualViolations(expectedValidations, user, UpdateEmailSequence.class);
    }

    @Test
    void givenUserWithNonExistingEmailAndTakenNewEmail_thenReturnConstraintValidationException() {
        User user = new User();
        user.setEmail("john.doe2@gmail.com");
        user.setNewEmail(existingUser.getEmail());
        Map<String, Object> expectedValidations = Map.of(
                "email", "does.not.exist",
                "newEmail", "user.email.already.exists");
        assertEqualViolations(expectedValidations, user, UpdateEmailSequence.class);
    }


}