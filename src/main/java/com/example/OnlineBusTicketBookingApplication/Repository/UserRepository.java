package com.example.OnlineBusTicketBookingApplication.Repository;

import com.example.OnlineBusTicketBookingApplication.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
