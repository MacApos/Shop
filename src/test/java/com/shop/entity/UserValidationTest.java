package com.shop.entity;

import com.shop.service.UserService;
import com.shop.validation.group.sequence.CreateSequence;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class UserValidationTest {

    @Autowired
    private UserService userService;

    private User newUser;
    private User invalidUser;
    private User existingUser;
    private User userWithExpiredToken;
    private Validator validator;

    @BeforeAll
    public void init() {
        newUser = new User("johnDoe", "John", "Doe", "Password123",
                "john.doe@gmail.com");
        invalidUser = new User("JD", "John", "Doe", "Password456",
                "j.d@gmail.com");
        existingUser = new User("janeDoe", "Jane", "Doe", "Password789",
                "jane.doe@gmail.com");
        userWithExpiredToken = new User("fooBar", "Foo", "Bar", "FooBar123",
                "foo.bar@gmail.com");
        userWithExpiredToken.setEnabled(true);
        userService.save(existingUser);
        userService.save(userWithExpiredToken);

        try(ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()){
            validator = validatorFactory.getValidator();
        }
    }

    @Test
    void givenUserWithTooShortUsername_whenCreateUser_thenReturnConstraintValidation() {
        Set<ConstraintViolation<User>> validation = validator.validate(existingUser, CreateSequence.class);
        assertThat(validation).isNotEmpty();
    }

    @Test
    void givenUserWithTakenUsername_whenCreateUser_thenReturnConstraintValidation() {
        Set<ConstraintViolation<User>> validation = validator.validate(existingUser, CreateSequence.class);
        assertThat(validation).isNotEmpty();
    }
}