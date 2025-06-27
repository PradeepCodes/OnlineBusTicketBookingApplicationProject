package com.example.OnlineBusTicketBookingApplication.Service;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;

import java.util.List;
import java.util.Optional;

public interface BusService {

    Bus saveBus(Bus bus);
    List<Bus> getAllBuses();
    Bus getBusById(Long id);
    void deleteBusById(Long id);
    List<Bus> searchBuses(String source, String destination);
}
