package com.example.OnlineBusTicketBookingApplication.RestController;


import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buses")
public class BusRestController {
    @Autowired
    private BusService busService;


   @GetMapping("/search")
    public ResponseEntity<List<Bus>> searchBuses(@RequestParam String source,
                                                 @RequestParam String destination) {
        List<Bus> buses = busService.searchBuses(source, destination);
        return ResponseEntity.ok(buses);
    }


    @PostMapping
    public ResponseEntity<Bus> addBus(@RequestBody Bus bus) {
        Bus savedBus = busService.saveBus(bus);
        return ResponseEntity.ok(savedBus);
    }


    @GetMapping
    public ResponseEntity<List<Bus>> getAllBuses() {
        return ResponseEntity.ok(busService.getAllBuses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bus> getBusById(@PathVariable Long id) {
        Bus bus = busService.getBusById(id);
        if (bus == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bus);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Bus> updateBus(@PathVariable Long id, @RequestBody Bus updatedBus) {
        Bus existingBus = busService.getBusById(id);
        if (existingBus == null) {
            return ResponseEntity.notFound().build();
        }

        existingBus.setBusNumber(updatedBus.getBusNumber());
        existingBus.setSource(updatedBus.getSource());
        existingBus.setDestination(updatedBus.getDestination());
        existingBus.setDepartureTime(updatedBus.getDepartureTime());
        existingBus.setArrivalTime(updatedBus.getArrivalTime());
        existingBus.setSeatsAvailable(updatedBus.getSeatsAvailable());
        existingBus.setFare(updatedBus.getFare());

        busService.saveBus(existingBus);
        return ResponseEntity.ok(existingBus);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBus(@PathVariable Long id) {
        busService.deleteBusById(id);
        return ResponseEntity.ok("✅ Bus deleted successfully");
    }
}
