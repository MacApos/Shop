package com.shop.entity;

import com.shop.mapper.UserMapper;
import com.shop.mapper.UserMapperImpl;
import com.shop.repository.UserRepository;
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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import({
//        RepositoryConfiguration.class,
        UserService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class UserValidationTest {
    @TestConfiguration
    static class UserValidationTestConfiguration {
//        @Bean
//        public PasswordEncoder encoder() {
//            return new BCryptPasswordEncoder();
//        }

        @Bean
        public UserMapper userMapper() {
            return new UserMapperImpl();
        }
    }

    @MockitoBean
    private PasswordEncoder encoder;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

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
        existingUser = new User("janeDoe", "Jane", "Doe",
                "P@ssword789", "P@ssword789", "jane.doe@gmail.com");
        userWithExpiredToken = new User("fooBar", "Foo", "Bar", "FooBar123",
                "foo.bar@gmail.com");
        existingUser.setEnabled(true);
        userService.save(existingUser);

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            validator = validatorFactory.getValidator();
        }
    }

    @Test
    void givenUserWithTooShortUsername_whenCreateUser_thenReturnConstraintValidation() {
//        userRepository.validate(existingUser, CreateSequence.class);
//        Set<ConstraintViolation<User>> validation = validator.validate(existingUser, CreateSequence.class);
//        assertThat(validation).isNotEmpty();
    }

//    @Test
//    void givenUserWithTakenUsername_whenCreateUser_thenReturnConstraintValidation() {
//        Set<ConstraintViolation<User>> validation = validator.validate(existingUser, CreateSequence.class);
//        assertThat(validation).isNotEmpty();
//    }
}