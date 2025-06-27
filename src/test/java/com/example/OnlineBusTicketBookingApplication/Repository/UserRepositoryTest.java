package com.example.OnlineBusTicketBookingApplication.Repository;

import com.example.OnlineBusTicketBookingApplication.Entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Test findByEmail returns user when present")
    void testFindByEmailSuccess() {
        // Arrange
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.USER);
        userRepository.save(user);

        // Act
        Optional<User> found = userRepository.findByEmail("test@example.com");

        // Assert
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Test User");
        assertThat(found.get().getRole()).isEqualTo(User.Role.USER);
    }

    @Test
    @DisplayName("Test findByEmail returns empty when user not present")
    void testFindByEmailNotFound() {
        Optional<User> found = userRepository.findByEmail("notfound@example.com");
        assertThat(found).isEmpty();
    }
}
