package com.example.OnlineBusTicketBookingApplication.Controller;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Service.BookingService;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import com.example.OnlineBusTicketBookingApplication.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingControllerTest {

    @Mock
    private BookingService bookingService;

    @Mock
    private BusService busService;

    @Mock
    private UserService userService;

    @InjectMocks
    private BookingController bookingController;

    private User testUser;
    private Bus testBus;
    private Principal principal;
    private Model model;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("John");
        testUser.setEmail("john@example.com");

        testBus = new Bus();
        testBus.setId(1L);
        testBus.setBusNumber("BUS123");
        testBus.setFare(300.0);

        principal = () -> "john@example.com";
        model = new BindingAwareModelMap();
    }

    @Test
    void testBookBusSuccess() {
        when(busService.getBusById(1L)).thenReturn(testBus);
        when(userService.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));

        String viewName = bookingController.bookBus(1L, 2, principal, model);

        assertEquals("bookingSuccess", viewName);
        assertTrue(model.containsAttribute("booking"));
        verify(bookingService, times(1)).saveBooking(any(Booking.class));
    }

    @Test
    void testBookBusUserNotFound() {
        when(busService.getBusById(1L)).thenReturn(testBus);
        when(userService.findByEmail("john@example.com")).thenReturn(Optional.empty());

        String view = bookingController.bookBus(1L, 2, principal, model);

        assertEquals("redirect:/bookings/my", view);
        verify(bookingService, never()).saveBooking(any());
    }

    @Test
    void testMyBookings() {
        when(userService.findByEmail("john@example.com")).thenReturn(Optional.of(testUser));
        when(bookingService.getBookingsByUser(testUser)).thenReturn(Collections.emptyList());

        String view = bookingController.myBookings(principal, model);

        assertEquals("myBookings", view);
        assertTrue(model.containsAttribute("bookings"));
    }

    @Test
    void testCancelBooking() {
        String result = bookingController.cancelBooking(10L);

        assertEquals("redirect:/bookings/my", result);
        verify(bookingService, times(1)).cancelBooking(10L);
    }
}
