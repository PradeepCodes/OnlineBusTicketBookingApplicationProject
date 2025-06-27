package com.example.OnlineBusTicketBookingApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tbl_payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Booking booking;

    private String paymentMode; // e.g. CREDIT_CARD, UPI
    private String paymentStatus; // e.g. SUCCESS, FAILED

}
