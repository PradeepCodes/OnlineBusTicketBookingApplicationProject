package com.example.OnlineBusTicketBookingApplication.Service.Impl;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Repository.BookingRepository;
import com.example.OnlineBusTicketBookingApplication.Service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public Booking saveBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getBookingsByUser(User user) {
        return bookingRepository.findByUser(user);
    }

    @Override
    public void cancelBooking(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Optional<Booking> getBookingById(Long id) {
        return bookingRepository.findById(id);
    }
}
