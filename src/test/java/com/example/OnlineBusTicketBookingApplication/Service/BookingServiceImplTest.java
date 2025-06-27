package com.example.OnlineBusTicketBookingApplication.Service.Impl;

import com.example.OnlineBusTicketBookingApplication.Entity.Booking;
import com.example.OnlineBusTicketBookingApplication.Entity.User;
import com.example.OnlineBusTicketBookingApplication.Repository.BookingRepository;
import com.example.OnlineBusTicketBookingApplication.Service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking sampleBooking;
    private User sampleUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleUser = new User();
        sampleUser.setId(1L);
        sampleUser.setEmail("user@example.com");
        sampleUser.setName("Test User");

        sampleBooking = new Booking();
        sampleBooking.setId(100L);
        sampleBooking.setUser(sampleUser);
        sampleBooking.setSeatsBooked(2);
        sampleBooking.setTotalFare(500);
        sampleBooking.setBookingDate(LocalDate.now());
    }

    @Test
    void saveBooking_shouldSaveAndReturnBooking() {
        when(bookingRepository.save(sampleBooking)).thenReturn(sampleBooking);

        Booking saved = bookingService.saveBooking(sampleBooking);

        assertNotNull(saved);
        assertEquals(2, saved.getSeatsBooked());
        verify(bookingRepository, times(1)).save(sampleBooking);
    }

    @Test
    void getBookingsByUser_shouldReturnListOfBookings() {
        when(bookingRepository.findByUser(sampleUser)).thenReturn(List.of(sampleBooking));

        List<Booking> bookings = bookingService.getBookingsByUser(sampleUser);

        assertEquals(1, bookings.size());
        assertEquals(sampleBooking, bookings.get(0));
        verify(bookingRepository).findByUser(sampleUser);
    }

    @Test
    void cancelBooking_shouldDeleteBookingById() {
        bookingService.cancelBooking(100L);
        verify(bookingRepository, times(1)).deleteById(100L);
    }

    @Test
    void getBookingById_shouldReturnBooking() {
        when(bookingRepository.findById(100L)).thenReturn(Optional.of(sampleBooking));

        Optional<Booking> result = bookingService.getBookingById(100L);

        assertTrue(result.isPresent());
        assertEquals(500, result.get().getTotalFare());
        verify(bookingRepository).findById(100L);
    }
}
