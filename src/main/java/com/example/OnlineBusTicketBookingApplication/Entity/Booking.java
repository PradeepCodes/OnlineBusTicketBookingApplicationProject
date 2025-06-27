package com.example.OnlineBusTicketBookingApplication.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Table(name="tbl_booking")

public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Bus bus;

    private LocalDate bookingDate;
    private int seatsBooked;
    private double totalFare;
}
