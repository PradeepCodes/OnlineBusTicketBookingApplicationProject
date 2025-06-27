package com.example.OnlineBusTicketBookingApplication.Service.Impl;

import com.example.OnlineBusTicketBookingApplication.DTO.PasswordChangeDTO;
import com.example.OnlineBusTicketBookingApplication.DTO.UserProfileDTO;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Repository.UserRepository;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email); // ✅ using email
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(User.Role.USER); // Default role
        return userRepository.save(user);
    }
    public void updateProfile(String email, UserProfileDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(dto.getName());
        userRepository.save(user);
        }

    @Override
    public boolean changePassword(String email, PasswordChangeDTO dto) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (passwordEncoder.matches(dto.getOldPassword(), user.getPassword()) &&
                dto.getNewPassword().equals(dto.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}

