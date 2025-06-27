package com.example.OnlineBusTicketBookingApplication.RestController;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.BookingService;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    @Autowired
    private UserService userService;

    @PostMapping("/book/{busId}")
    public ResponseEntity<?> bookBus(@PathVariable Long busId,
                                     @RequestParam int seats,
                                     @AuthenticationPrincipal UserDetails userDetails) {

        Optional<User> userOpt = userService.findByEmail(userDetails.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(" User not found");
        }

        Bus bus = busService.getBusById(busId);
        if (bus == null) {
            return ResponseEntity.badRequest().body(" Bus not found");
        }

        Booking booking = new Booking();
        booking.setUser(userOpt.get());
        booking.setBus(bus);
        booking.setSeatsBooked(seats);
        booking.setBookingDate(LocalDate.now());
        booking.setTotalFare(seats * bus.getFare());

        bookingService.saveBooking(booking);
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/my")
    public ResponseEntity<?> myBookings(@AuthenticationPrincipal UserDetails userDetails) {
        Optional<User> userOpt = userService.findByEmail(userDetails.getUsername());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(" User not found");
        }

        List<Booking> bookings = bookingService.getBookingsByUser(userOpt.get());
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return ResponseEntity.ok(" Booking cancelled successfully.");
    }
}
