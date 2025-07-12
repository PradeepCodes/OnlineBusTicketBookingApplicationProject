package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.EmailService;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
import java.util.UUID;

@Controller
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user) {
        user.setRole(User.Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.saveUser(user);
        return "redirect:/login?success";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String userDashboard(Authentication auth, Model model) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "admin-dashboard"; // Thymeleaf page: admin-dashboard.html
        } else if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_USER"))) {
            return "user-dashboard"; // Thymeleaf page: user-dashboard.html
        }
        return "dashboard";
    }
    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }

//    @PostMapping("/forgot-password")
//    public String processForgotPassword(@RequestParam String email,
//                                        HttpServletRequest request, // ✅ Add this
//                                        Model model) {
//        Optional<User> userOptional = userService.findByEmail(email);
//
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            String token = UUID.randomUUID().toString();
//            user.setResetToken(token);
//            userService.saveUser(user); // Save token
//
//            // 🔗 Build reset password link
//            String resetLink = request.getScheme() + "://" + request.getServerName() + ":" +
//                    request.getServerPort() + "/reset-password?token=" + token;
//
//            // Just printing for now – replace with actual email service
//            System.out.println("🔗 Password Reset Link: " + resetLink);
//
//            model.addAttribute("message", "Password reset link sent to your email!");
//        } else {
//            model.addAttribute("error", "No user found with that email.");
//        }
//
//        return "forgot-password";
//    }
@PostMapping("/forgot-password")
public String processForgotPassword(@RequestParam String email,
                                    Model model,
                                    HttpServletRequest request) {
    Optional<User> userOptional = userService.findByEmail(email);

    if (userOptional.isPresent()) {
        User user = userOptional.get();
        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userService.saveUser(user);

        //localhost
//        String resetLink = request.getScheme() + "://" + request.getServerName() + ":" +
//                request.getServerPort() + "/reset-password?token=" + token;

        String resetLink = request.getRequestURL().toString()
                .replace(request.getServletPath(), "")
                + "/reset-password?token=" + token;

        // ✅ Send email
        emailService.sendResetPasswordEmail(email, resetLink);

        model.addAttribute("message", "Reset link sent to your email!");
    } else {
        model.addAttribute("error", "No user found with that email.");
    }

    return "forgot-password";
}
    @GetMapping("/reset-password")
    public String showResetForm(@RequestParam("token") String token, Model model) {
        Optional<User> userOptional = userService.findByResetToken(token);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Invalid or expired reset token.");
            return "forgot-password";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String newPassword,
                                       @RequestParam String confirmPassword,
                                       Model model) {
        Optional<User> userOptional = userService.findByResetToken(token);
        if (userOptional.isEmpty()) {
            model.addAttribute("error", "Invalid token.");
            return "reset-password";
        }

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("token", token);
            model.addAttribute("error", "Passwords do not match.");
            return "reset-password";
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetToken(null); // Invalidate token
        userService.saveUser(user);

        model.addAttribute("message", "✅ Password successfully reset! Please login.");
        return "login";
    }

}
