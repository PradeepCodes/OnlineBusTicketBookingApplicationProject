package com.example.OnlineBusTicketBookingApplication.Service;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.User;

import java.util.List;
import java.util.Optional;

public interface BookingService {
    Booking saveBooking(Booking booking);
    List<Booking> getBookingsByUser(User user);
    void cancelBooking(Long id);
    Optional<Booking> getBookingById(Long id);

}
