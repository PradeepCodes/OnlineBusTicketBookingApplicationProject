package com.example.OnlineBusTicketBookingApplication.Repository;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus, Long> {
    @Query("SELECT DISTINCT b.source FROM Bus b")
    List<String> findDistinctSources();

    @Query("SELECT DISTINCT b.destination FROM Bus b")
    List<String> findDistinctDestinations();


    List<Bus> findBySourceAndDestination(String source, String destination);
}
