package com.example.OnlineBusTicketBookingApplication.Repository;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusRepository extends JpaRepository<Bus, Long> {

}
