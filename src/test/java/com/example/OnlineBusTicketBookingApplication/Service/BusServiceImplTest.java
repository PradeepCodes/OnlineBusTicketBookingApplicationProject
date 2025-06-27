package com.example.OnlineBusTicketBookingApplication.Service.Impl;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Repository.BusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BusServiceImplTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusServiceImpl busService;

    private Bus sampleBus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleBus = new Bus();
        sampleBus.setId(1L);
        sampleBus.setBusNumber("TN01AB1234");
        sampleBus.setSource("Chennai");
        sampleBus.setDestination("Madurai");
    }

    @Test
    void saveBus_shouldReturnSavedBus() {
        when(busRepository.save(sampleBus)).thenReturn(sampleBus);

        Bus saved = busService.saveBus(sampleBus);

        assertNotNull(saved);
        assertEquals("TN01AB1234", saved.getBusNumber());
        verify(busRepository).save(sampleBus);
    }

    @Test
    void getAllBuses_shouldReturnListOfBuses() {
        List<Bus> buses = Arrays.asList(sampleBus);
        when(busRepository.findAll()).thenReturn(buses);

        List<Bus> result = busService.getAllBuses();

        assertEquals(1, result.size());
        assertEquals("Chennai", result.get(0).getSource());
        verify(busRepository).findAll();
    }

    @Test
    void getBusById_shouldReturnBusIfExists() {
        when(busRepository.findById(1L)).thenReturn(Optional.of(sampleBus));

        Bus bus = busService.getBusById(1L);

        assertNotNull(bus);
        assertEquals("Madurai", bus.getDestination());
        verify(busRepository).findById(1L);
    }

    @Test
    void getBusById_shouldThrowExceptionIfNotFound() {
        when(busRepository.findById(99L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            busService.getBusById(99L);
        });

        assertEquals("Bus not found", ex.getMessage());
        verify(busRepository).findById(99L);
    }

    @Test
    void searchBuses_shouldReturnMatchingBuses() {
        Bus bus2 = new Bus();
        bus2.setId(2L);
        bus2.setSource("Chennai");
        bus2.setDestination("Madurai");

        when(busRepository.findAll()).thenReturn(List.of(sampleBus, bus2));

        List<Bus> result = busService.searchBuses("chennai", "madurai");

        assertEquals(2, result.size());
        verify(busRepository).findAll();
    }

    @Test
    void deleteBusById_shouldInvokeRepositoryDelete() {
        busService.deleteBusById(1L);
        verify(busRepository, times(1)).deleteById(1L);
    }
}
