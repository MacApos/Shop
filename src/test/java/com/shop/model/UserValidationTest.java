package com.shop.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.service.MessageService;
import com.shop.service.UserService;
import com.shop.validation.user.group.sequence.CreateUserSequence;
import com.shop.validation.user.group.sequence.UpdateEmailSequence;
import com.shop.validation.user.group.sequence.UpdateUserSequence;
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

    private List<Tuple> mapToTupleList(Map<String, Object> map) {
        return map.entrySet().stream().flatMap(e -> {
            if (e.getValue() instanceof List<?> values) {
                return values.stream().map(v -> tuple(e.getKey(), v));
            }
            return Stream.of(tuple(e.getKey(), e.getValue()));
        }).toList();
    }

    private void assertEqualViolations(List<Tuple> expectedViolations, User user, Class<?>... groups) {
        Set<ConstraintViolation<User>> violations = factoryBean.validate(user, groups);
        assertThat(violations)
                .isNotEmpty()
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrderElementsOf(expectedViolations);
    }

    private void assertEqualViolations(Map<String, Object> expectedViolations, User user, Class<?>... groups) {
        List<Tuple> tuples = mapToTupleList(expectedViolations)
                .stream()
                .map(Tuple::toList)
                .map(list -> tuple(list.get(0), messageService.getMessage((String) list.get(1)))).toList();
        assertEqualViolations(tuples, user, groups);
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
        assertEqualViolations(expectedValidations, user, CreateUserSequence.class);
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
        List<Tuple> tuples = mapToTupleList(expectedViolations);
        assertEqualViolations(tuples, user, CreateUserSequence.class);
        assertEqualViolations(tuples, user, UpdateUserSequence.class);
    }

    @Test
    void givenUserWithTakenUsernameAndEmail_thenReturnConstraintValidationException() {
        Map<String, Object> expectedViolations = new HashMap<>();
        expectedViolations.put("username", "user.username.already.exists");
        assertEqualViolations(expectedViolations, existingUser, UpdateUserSequence.class);
        expectedViolations.put("email", "user.email.already.exists");
        assertEqualViolations(expectedViolations, existingUser, CreateUserSequence.class);
    }

    @Test
    void givenUserWithInvalidEmail_thenReturnConstraintValidationException() {
        User user = copyUser(newUser);
        user.setEmail("johndoegmailcom");
        Map<String, Object> expectedViolations = Map.of("email", invalidEmail);
        assertEqualViolations(expectedViolations, user, CreateUserSequence.class);
    }

    @Test
    void givenUserWithNullPassword_thenReturnConstraintValidationException() {
        User user = copyUser(existingUser);
        user.setPassword(null);
        user.setPasswordConfirm(null);
        Map<String, Object> expectedViolations = Map.of(
                "password", nullMessage,
                "passwordConfirm", nullMessage);
        assertEqualViolations(expectedViolations, user, CreateUserSequence.class);
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
                        messageService.getMessage("INSUFFICIENT_LOWERCASE").replace("%1$s", "1"),
                        messageService.getMessage("INSUFFICIENT_DIGIT").replace("%1$s", "1")),
                "passwordConfirm", messageService.getMessage(invalidPasswordConfirmation)
        );
        List<Tuple> tuples = mapToTupleList(expectedViolations);
        assertEqualViolations(tuples, user, CreateUserSequence.class);
    }

    @Test
    void givenUserInvalidPasswordConfirm_thenReturnConstraintValidationException() {
        User user = copyUser(newUser);
        user.setPasswordConfirm("password");
        Map<String, Object> expectedViolations = Map.of("passwordConfirm", invalidPasswordConfirmation);
        assertEqualViolations(expectedViolations, user, CreateUserSequence.class);
    }

    @Test
    void givenUserInvalidEmailAndNewEmail_thenReturnConstraintValidationException() {
        User user = new User();
        user.setEmail("johndoegmailcom");
        user.setNewEmail("johndoe2gmailcom");
        Map<String, Object> expectedViolations = Map.of(
                "email", invalidEmail,
                "newEmail", invalidEmail);
        assertEqualViolations(expectedViolations, user, UpdateEmailSequence.class);
    }

    @Test
    void givenUserInvalidNewEmail_thenReturnConstraintValidationException() {
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setNewEmail("janendoe2gmailcom");
        Map<String, Object> expectedViolations = Map.of("newEmail", invalidEmail);
        assertEqualViolations(expectedViolations, user, UpdateEmailSequence.class);
    }

    @Test
    void givenUserWithNonExistingEmailAndTakenNewEmail_thenReturnConstraintValidationException() {
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setNewEmail(existingUser.getEmail());
        Map<String, Object> expectedViolations = Map.of(
                "email", "does.not.exist",
                "newEmail", "user.email.already.exists");
        assertEqualViolations(expectedViolations, user, UpdateEmailSequence.class);
    }

}