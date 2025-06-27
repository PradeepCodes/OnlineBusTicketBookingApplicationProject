package com.example.OnlineBusTicketBookingApplication.RestController;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BusRestController.class)
class BusRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BusService busService;

    private Bus sampleBus;

    @BeforeEach
    void setUp() {
        sampleBus = new Bus();
        sampleBus.setId(1L);
        sampleBus.setBusNumber("TN-01-1234");
        sampleBus.setSource("Chennai");
        sampleBus.setDestination("Madurai");
        sampleBus.setDepartureTime(LocalDateTime.of(2025, 7, 1, 9, 0));
        sampleBus.setArrivalTime(LocalDateTime.of(2025, 7, 1, 15, 0));
        sampleBus.setSeatsAvailable(40);
        sampleBus.setFare(300.0);
    }

    @Test
    void testGetAllBuses() throws Exception {
        when(busService.getAllBuses()).thenReturn(List.of(sampleBus));

        mockMvc.perform(get("/api/buses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(1)))
                .andExpect(jsonPath("$[0].busNumber", is("TN-01-1234")));
    }

    @Test
    void testGetBusById() throws Exception {
        when(busService.getBusById(1L)).thenReturn(sampleBus);

        mockMvc.perform(get("/api/buses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.source", is("Chennai")))
                .andExpect(jsonPath("$.destination", is("Madurai")));
    }

    @Test
    void testSearchBuses() throws Exception {
        when(busService.searchBuses("Chennai", "Madurai"))
                .thenReturn(List.of(sampleBus));

        mockMvc.perform(get("/api/buses/search")
                        .param("source", "Chennai")
                        .param("destination", "Madurai"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].busNumber", is("TN-01-1234")));
    }

    @Test
    void testCreateBus() throws Exception {
        when(busService.saveBus(any(Bus.class))).thenReturn(sampleBus);

        String busJson = """
                {
                  "busNumber": "TN-01-1234",
                  "source": "Chennai",
                  "destination": "Madurai",
                  "departureTime": "2025-07-01T09:00:00",
                  "arrivalTime": "2025-07-01T15:00:00",
                  "seatsAvailable": 40,
                  "fare": 300.0
                }
                """;

        mockMvc.perform(post("/api/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(busJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.busNumber", is("TN-01-1234")));
    }

    @Test
    void testDeleteBus() throws Exception {
        doNothing().when(busService).deleteBusById(1L);

        mockMvc.perform(delete("/api/buses/1"))
                .andExpect(status().isOk());

        verify(busService, times(1)).deleteBusById(1L);
    }
}
