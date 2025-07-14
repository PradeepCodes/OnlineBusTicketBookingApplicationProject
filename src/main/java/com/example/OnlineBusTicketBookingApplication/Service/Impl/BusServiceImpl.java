package com.example.OnlineBusTicketBookingApplication.Service.Impl;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Repository.BusRepository;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BusServiceImpl implements BusService {
    @Autowired
    private BusRepository busRepository;

    @Override
    public Bus saveBus(Bus bus) {
        return busRepository.save(bus);
    }


    @Override
    public List<String> getAllSources() {
        return busRepository.findDistinctSources();
    }

    @Override
    public List<String> getAllDestinations() {
        return busRepository.findDistinctDestinations();
    }

    @Override
    public List<Bus> getAllBuses() {

        return busRepository.findAll();
    }

    @Override
    public Bus getBusById(Long id) {
        return busRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bus not found"));
    }

    @Override
    public List<Bus> searchBuses(String source, String destination) {
        return busRepository.findAll().stream()
                .filter(bus -> bus.getSource().equalsIgnoreCase(source) &&
                        bus.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteBusById(Long id) {
        busRepository.deleteById(id);
    }
}
