package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.BookingService;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private BusService busService;

    @Autowired
    private UserService userService;

    @PostMapping("/book/{busId}")
    public String bookBus(@PathVariable Long busId,
                          @RequestParam int seats,
                          Principal principal,
                          Model model) {
        // ✅ Get bus directly (not Optional)
        Bus bus = busService.getBusById(busId);

        Optional<User> userOpt = userService.findByEmail(principal.getName());

        if (userOpt.isPresent()) {
            User user = userOpt.get();

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setBus(bus);
            booking.setSeatsBooked(seats);
            booking.setBookingDate(LocalDate.now());
            booking.setTotalFare(seats * bus.getFare());

            bookingService.saveBooking(booking);
            model.addAttribute("booking", booking);
            return "bookingSuccess";
        }

        return "redirect:/bookings/my";

    }

    @GetMapping("/my")
    public String myBookings(Principal principal, Model model) {
        Optional<User> user = userService.findByEmail(principal.getName());
        if (user.isPresent()) {
            List<Booking> bookings = bookingService.getBookingsByUser(user.get());
            model.addAttribute("bookings", bookings);
        }
        return "myBookings";
    }

    @GetMapping("/cancel/{id}")
    public String cancelBooking(@PathVariable Long id) {
        bookingService.cancelBooking(id);
        return "redirect:/bookings/my";
    }
}
