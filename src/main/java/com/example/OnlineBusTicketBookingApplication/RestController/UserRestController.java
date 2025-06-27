package com.example.OnlineBusTicketBookingApplication.RestController;

import com.example.OnlineBusTicketBookingApplication.DTO.PasswordChangeDTO;
import com.example.OnlineBusTicketBookingApplication.DTO.UserProfileDTO;
import com.example.OnlineBusTicketBookingApplication.DTO.UserRegistrationDTO;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ Registration successful.");
    }
    @GetMapping
    public  ResponseEntity<String>  sayHello() {
        return ResponseEntity.status(HttpStatus.CREATED).body("✅ Registration successful.");
    }

}
