//package com.shop.entity;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.shop.service.MessageService;
//import com.shop.validation.group.sequence.CreateSequence;
//import jakarta.persistence.EntityManager;
//import jakarta.validation.ConstraintViolation;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestInstance;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
//
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
////@DataJpaTest
////@Import({UserService.class, MessageService.class})
//@SpringBootTest
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestPropertySource("/application-test.properties")
//class UserValidationTest2 {
////    @TestConfiguration
////    static class UserValidationTestConfiguration {
////        @Bean
////        public MessageSource messageSource() {
////            ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
////            messageSource.setBasename("classpath:messages");
////            messageSource.setDefaultEncoding("UTF-8");
////            return messageSource;
////        }
////
////        @Bean
////        public UserMapper userMapper() {
////            return new UserMapperImpl();
////        }
////
////        @Bean
////        public LocalValidatorFactoryBean factoryBean() {
////            return new LocalValidatorFactoryBean();
////        }
////    }
//
//    @MockitoBean
//    private PasswordEncoder encoder;
//
//    @Autowired
//    private MessageService messageService;
//
//    @Autowired
//    private LocalValidatorFactoryBean factoryBean;
//
//    @Autowired
//    private EntityManager entityManager;
//
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private User takenUsernameAndEmail;
//    private User invalidData;
//    private User invalidPassword;
//
//    @BeforeAll
//    public void init() {
//        User existingUser = new User("johnDoe", "John", "Doe",
//                "P@ssword123", "P@ssword123", "john.doe@gmail.com");
//        takenUsernameAndEmail = copyUser(existingUser);
//        invalidData = new User("JD", "John", null,
//                "P@ssword456", "P@ssword456", "j.d@gmail.com");
//        invalidPassword = new User();
//        invalidPassword.setPassword("pass");
//
//        existingUser.setEnabled(true);
//        entityManager.persist(existingUser);
//        entityManager.flush();
//    }
//
//    private User copyUser(User user)  {
//        User result = objectMapper.convertValue(user, User.class);
//        result.setPassword(user.getPassword());
//        result.setPasswordConfirm(user.getPasswordConfirm());
//        return result;
//    }
//
//    @Test
//    void givenUserWithInvalidData_thenReturnConstraintValidationException() {
//        String nullMessage = messageService.getMessage("jakarta.validation.constraints.NotNull.message");
//        String sizeMessage = messageService.getMessage("jakarta.validation.constraints.Size.message")
//                .replace("{min}", "3");
//        Set<ConstraintViolation<User>> violations = factoryBean.validate(invalidData, CreateSequence.class);
//        assertThat(violations)
//                .extracting(ConstraintViolation::getMessage)
//                .containsExactlyInAnyOrder(nullMessage, sizeMessage);
//    }
//
//    @Test
//    void givenUserWithTakenUsernameAndEmail_thenReturnConstraintValidationException() {
//        String usernameTaken = messageService.getMessage("user.username.already.exists");
//        String emailTaken = messageService.getMessage("user.email.already.exists");
//        Set<ConstraintViolation<User>> violations = factoryBean.validate(takenUsernameAndEmail, CreateSequence.class);
//        assertThat(violations)
//                .extracting(ConstraintViolation::getMessage)
//                .containsExactlyInAnyOrder(usernameTaken, emailTaken);
//    }
//
//    @Test
//    void givenUserInvalidPassword_thenReturnConstraintValidationException() {
//        String tooLong = messageService.getMessage("TOO_LONG");
//        String tooLong = messageService.getMessage("INSUFFICIENT_UPPERCASE");
//        String tooLong = messageService.getMessage("INSUFFICIENT_DIGIT");
//        String tooLong = messageService.getMessage("INSUFFICIENT_SPECIAL");
//        String emailTaken = messageService.getMessage("user.email.already.exists");
//        Set<ConstraintViolation<User>> violations = factoryBean.validate(takenUsernameAndEmail, CreateSequence.class);
//        assertThat(violations)
//                .extracting(ConstraintViolation::getMessage)
//                ;
//    }
//}