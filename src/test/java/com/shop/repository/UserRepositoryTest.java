package com.shop.repository;

import com.shop.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User newUser;

    @BeforeEach
    public void initProducts() {
        newUser = new User("johnDoe", "John", "Doe", "Password123", "john.doe@gmail.com");
        newUser.setEnabled(true);
        entityManager.persist(newUser);
        entityManager.flush();
    }

    @Test
    public void givenUsername_whenFindByUsername_thenReturnUserWithGivenUsername() {
        User user = userRepository.findByUsername("johnDoe");
        assertThat(newUser).usingRecursiveComparison().isEqualTo(user);
    }
}
