package com.example.OnlineBusTicketBookingApplication.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "tbl_bus")
public class Bus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String busNumber;
    private String source;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int seatsAvailable;
    private double fare;

    public Bus() {
        // No-arg constructor
    }

    public Bus(String busNumber, String source, String destination,
               LocalDateTime departureTime, LocalDateTime arrivalTime,
               int seatsAvailable, double fare) {
        this.busNumber = busNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.seatsAvailable = seatsAvailable;
        this.fare = fare;
    }

}
