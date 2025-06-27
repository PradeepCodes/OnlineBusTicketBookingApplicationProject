package com.example.OnlineBusTicketBookingApplication.Repository;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookingRepositoryTest {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BusRepository busRepository;

    @Test
    @DisplayName("Test findByUser returns bookings for a user")
    void testFindByUser() {
        // Create and save a User
        User user = new User();
        user.setName("Test User");
        user.setEmail("test@example.com");
        user.setPassword("pass");
        user.setRole(User.Role.USER);
        userRepository.save(user);

        // Create and save a Bus
        Bus bus = new Bus();
        bus.setBusNumber("TN1234");
        bus.setSource("Chennai");
        bus.setDestination("Bangalore");
        bus.setFare(500.0);
        busRepository.save(bus);

        // Create and save a Booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBus(bus);
        booking.setSeatsBooked(2);
        booking.setBookingDate(LocalDate.now());
        booking.setTotalFare(1000.0);
        bookingRepository.save(booking);

        // Verify findByUser
        List<Booking> bookings = bookingRepository.findByUser(user);

        assertThat(bookings).isNotEmpty();
        assertThat(bookings.get(0).getUser().getEmail()).isEqualTo("test@example.com");
        assertThat(bookings.get(0).getBus().getBusNumber()).isEqualTo("TN1234");
    }
}
