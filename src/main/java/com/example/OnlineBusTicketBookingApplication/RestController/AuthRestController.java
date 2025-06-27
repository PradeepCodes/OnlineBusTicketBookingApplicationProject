package com.example.OnlineBusTicketBookingApplication.RestController;

import com.example.OnlineBusTicketBookingApplication.Component.JwtUtil;
import com.example.OnlineBusTicketBookingApplication.DTO.LoginRequest;
import com.example.OnlineBusTicketBookingApplication.DTO.LoginResponse;
import com.example.OnlineBusTicketBookingApplication.Service.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            String token = jwtTokenUtil.generateToken(authentication.getName());
            return ResponseEntity.ok(new LoginResponse(token));

        } catch (Exception e) {
            return ResponseEntity
                    .status(401)
                    .body(new LoginResponse("Invalid email or password"));
        }
    }

}
