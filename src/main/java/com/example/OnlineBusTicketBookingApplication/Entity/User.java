package com.example.OnlineBusTicketBookingApplication.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(unique = true, nullable = false)
    private String email; // ✅ use email

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public enum Role {
        ADMIN, USER
    }

}
