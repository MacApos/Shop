package com.shop.entity;

import com.shop.mapper.UserMapper;
import com.shop.mapper.UserMapperImpl;
import com.shop.repository.UserRepository;
import com.shop.service.MessageService;
import com.shop.service.UserService;
import com.shop.validation.group.sequence.CreateSequence;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@Import(UserService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("test")
class UserValidationTest {
    @TestConfiguration
    static class UserValidationTestConfiguration {
        @Bean
        public MessageService messageSourceService(MessageSource messageSource){
            return new MessageService(messageSource);
        }

        @Bean
        public UserMapper userMapper() {
            return new UserMapperImpl();
        }

        @Bean
        public LocalValidatorFactoryBean factoryBean() {
            return new LocalValidatorFactoryBean();
        }
    }

    @MockitoBean
    private PasswordEncoder encoder;

    @Autowired
    private LocalValidatorFactoryBean factoryBean;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    private User newUser;
    private User invalidUser;
    private User existingUser;
    private User userWithExpiredToken;

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
    }

    @Test
    void givenUserWithTooShortUsername_whenCreateUser_thenReturnConstraintValidation() {
      
        String message = messageService.getMessage("expired.token", locale);

//        assertThatThrownBy(() -> userService.validate(existingUser, CreateSequence.class))
//                .isInstanceOf(ConstraintViolationException.class)
//                .hasMessage("");
    }

//    @Test
//    void givenUserWithTakenUsername_whenCreateUser_thenReturnConstraintValidation() {
//        Set<ConstraintViolation<User>> validation = validator.validate(existingUser, CreateSequence.class);
//        assertThat(validation).isNotEmpty();
//    }
}