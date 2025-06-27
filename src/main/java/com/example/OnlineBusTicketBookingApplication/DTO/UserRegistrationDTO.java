package com.example.OnlineBusTicketBookingApplication.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String name;

    @Email
    private String email;

    private String password;
}
