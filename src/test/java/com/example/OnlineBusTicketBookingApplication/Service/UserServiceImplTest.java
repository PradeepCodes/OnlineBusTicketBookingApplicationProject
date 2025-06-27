package com.example.OnlineBusTicketBookingApplication.Service;

import com.example.OnlineBusTicketBookingApplication.DTO.PasswordChangeDTO;
import com.example.OnlineBusTicketBookingApplication.DTO.UserProfileDTO;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Repository.UserRepository;
import com.example.OnlineBusTicketBookingApplication.Service.Impl.UserServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("John");
        sampleUser.setEmail("john@example.com");
        sampleUser.setPassword("encodedPassword");
        sampleUser.setRole(User.Role.USER);
    }

    @Test
    void saveUser_shouldSaveAndReturnUser() {
        when(userRepository.save(sampleUser)).thenReturn(sampleUser);

        User saved = userService.saveUser(sampleUser);
        assertNotNull(saved);
        assertEquals("John", saved.getName());
    }

    @Test
    void findByEmail_shouldReturnOptionalUser() {
        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));

        Optional<User> result = userService.findByEmail("john@example.com");
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getName());
    }

    @Test
    void getAllUsers_shouldReturnUserList() {
        when(userRepository.findAll()).thenReturn(List.of(sampleUser));

        List<User> users = userService.getAllUsers();
        assertEquals(1, users.size());
    }

    @Test
    void updateProfile_shouldUpdateUserName() {
        UserProfileDTO dto = new UserProfileDTO();
        dto.setName("Updated Name");
        dto.setEmail("john@example.com");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));

        userService.updateProfile("john@example.com", dto);
        verify(userRepository).save(argThat(user -> user.getName().equals("Updated Name")));
    }

    @Test
    void changePassword_shouldSucceed() {
        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setOldPassword("oldpass");
        dto.setNewPassword("newpass");
        dto.setConfirmPassword("newpass");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("oldpass", "encodedPassword")).thenReturn(true);
        when(passwordEncoder.encode("newpass")).thenReturn("newEncoded");

        boolean result = userService.changePassword("john@example.com", dto);

        assertTrue(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void changePassword_shouldFailOnMismatch() {
        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setOldPassword("wrong");
        dto.setNewPassword("new");
        dto.setConfirmPassword("new");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("wrong", "encodedPassword")).thenReturn(false);

        boolean result = userService.changePassword("john@example.com", dto);

        assertFalse(result);
        verify(userRepository, never()).save(any());
    }

    @Test
    void changePassword_shouldFailOnNonMatchingNewPasswords() {
        PasswordChangeDTO dto = new PasswordChangeDTO();
        dto.setOldPassword("oldpass");
        dto.setNewPassword("newpass1");
        dto.setConfirmPassword("newpass2");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));
        when(passwordEncoder.matches("oldpass", "encodedPassword")).thenReturn(true);

        boolean result = userService.changePassword("john@example.com", dto);

        assertFalse(result);
        verify(userRepository, never()).save(any());
    }
}
