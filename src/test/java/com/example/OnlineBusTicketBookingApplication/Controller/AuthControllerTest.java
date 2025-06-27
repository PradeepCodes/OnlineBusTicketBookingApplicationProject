package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("password123");
        user.setRole(User.Role.USER);
    }

    @Test
    void testHomePage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    void testShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("user"))
                .andExpect(view().name("register"));
    }

    @Test
    void testRegisterUser() throws Exception {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPwd");

        mockMvc.perform(post("/register")
                        .param("name", user.getName())
                        .param("email", user.getEmail())
                        .param("password", user.getPassword()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?success"));

        verify(userService, times(1)).saveUser(ArgumentMatchers.any(User.class));
    }

    @Test
    void testShowLoginForm() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testDashboardRedirectsToAdmin() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin-dashboard"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    void testDashboardRedirectsToUser() throws Exception {
        mockMvc.perform(get("/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("user-dashboard"));
    }
}
