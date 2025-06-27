package com.example.OnlineBusTicketBookingApplication.Service;

import com.example.OnlineBusTicketBookingApplication.DTO.PasswordChangeDTO;
import com.example.OnlineBusTicketBookingApplication.DTO.UserProfileDTO;
import com.example.OnlineBusTicketBookingApplication.Entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User saveUser(User user);
    Optional<User> findByEmail(String email);
    List<User> getAllUsers();
    User registerUser(User user);

    void updateProfile(String email, UserProfileDTO dto);
    boolean changePassword(String email, PasswordChangeDTO dto);
}
