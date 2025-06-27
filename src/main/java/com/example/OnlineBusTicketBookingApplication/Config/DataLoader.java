package com.example.OnlineBusTicketBookingApplication.Config;

import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin@bus.com").isEmpty()) {
            User admin = new User();
            admin.setName("Admin");
            admin.setEmail("admin@bus.com");
            admin.setPassword(passwordEncoder.encode("admin123")); // Change to a secure password in prod
            admin.setRole(User.Role.ADMIN);
            userRepository.save(admin);

            System.out.println("✅ Default ADMIN user created: admin@bus.com / admin123");
        }
    }
}
