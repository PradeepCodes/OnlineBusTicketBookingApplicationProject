package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.BookingService;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import com.example.OnlineBusTicketBookingApplication.Service.EmailService;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import com.example.OnlineBusTicketBookingApplication.Util.PdfGeneratorUtil;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private BookingService bookingService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BusService busService;

    @Autowired
    private UserService userService;

    @GetMapping("/book/{busId}")
    public String showBookingForm(@PathVariable Long busId, Model model) {
        Bus bus = busService.getBusById(busId);
        model.addAttribute("bus", bus);
        return "bookingForm";  // New HTML page
    }

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

            String content = String.format("Dear %s,\n\nYour booking for bus %s (%s → %s) on %s is confirmed.\n" +
                            "Total Fare: ₹%.2f\n\nThank you for using our service!",
                    user.getName(), bus.getBusNumber(), bus.getSource(), bus.getDestination(),
                    bus.getDepartureTime().toLocalDate(), booking.getTotalFare());

            emailService.sendBookingConfirmationEmail(user.getEmail(), "🚌 Ticket Booking Confirmed", content);


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

    @GetMapping("/create-order")
    @ResponseBody
    public String createOrder(@RequestParam double amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_HRBmzxk9j6RKyo", "ZU4hTYJMJk98sKCMscCDTlcP");

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + UUID.randomUUID());
        options.put("payment_capture", 1);

        Order order = razorpay.orders.create(options);
        return order.toString();
    }


    @PostMapping("/create-order")
    @ResponseBody
    public String createOrders(@RequestParam double amount) throws RazorpayException {
        RazorpayClient razorpay = new RazorpayClient("rzp_test_HRBmzxk9j6RKyo", "ZU4hTYJMJk98sKCMscCDTlcP");

        JSONObject options = new JSONObject();
        options.put("amount", (int)(amount * 100)); // Amount in paise
        options.put("currency", "INR");
        options.put("receipt", "txn_" + UUID.randomUUID());
        options.put("payment_capture", 1);

        Order order = razorpay.orders.create(options);
        return order.toString();
    }

    @GetMapping("/pay")
    public String showPaymentPage(@RequestParam Long busId,
                                  @RequestParam(defaultValue = "1") int seats,
                                  Model model) {
        Bus bus = busService.getBusById(busId);
        model.addAttribute("bus", bus);
        model.addAttribute("seats", seats);
        model.addAttribute("amount", bus.getFare() * seats);
        return "payment-page"; // You'll create this HTML
    }

}
