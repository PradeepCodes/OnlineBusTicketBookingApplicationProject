package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.DTO.UserProfileDTO;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserProfileController.class)
class UserProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User sampleUser;

    @BeforeEach
    void setUp() {
        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setName("John Doe");
        sampleUser.setEmail("john@example.com");
        sampleUser.setPassword("encodedPassword");
    }

    @Test
    @WithMockUser(username = "john@example.com", roles = {"USER"})
    void showProfile_shouldReturnProfilePage() throws Exception {
        when(userService.findByEmail("john@example.com")).thenReturn(Optional.of(sampleUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("userProfile"))
                .andExpect(model().attributeExists("passwordChange"))
                .andExpect(model().attribute("username", "John Doe"));
    }

    @Test
    @WithMockUser(username = "john@example.com", roles = {"USER"})
    void updateProfile_shouldRedirectWithSuccess() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user/profile/update")
                        .param("name", "Updated Name")
                        .param("email", "john@example.com")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        Mockito.verify(userService).updateProfile(any(String.class), any(UserProfileDTO.class));
    }

    @Test
    @WithMockUser(username = "john@example.com", roles = {"USER"})
    void changePassword_success_shouldRedirectWithSuccess() throws Exception {
        when(userService.changePassword(Mockito.eq("john@example.com"), any()))
                .thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/profile/change-password")
                        .param("oldPassword", "old123")
                        .param("newPassword", "new123")
                        .param("confirmPassword", "new123")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        Mockito.verify(userService).changePassword(any(), any());
    }

    @Test
    @WithMockUser(username = "john@example.com", roles = {"USER"})
    void changePassword_failure_shouldRedirectWithError() throws Exception {
        when(userService.changePassword(Mockito.eq("john@example.com"), any()))
                .thenReturn(false);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/profile/change-password")
                        .param("oldPassword", "wrong")
                        .param("newPassword", "new123")
                        .param("confirmPassword", "new123")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"));

        Mockito.verify(userService).changePassword(any(), any());
    }
}
