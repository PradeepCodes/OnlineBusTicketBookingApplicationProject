package com.example.OnlineBusTicketBookingApplication.Config;

import com.example.OnlineBusTicketBookingApplication.Entity.Bus;
import com.example.OnlineBusTicketBookingApplication.Repository.BusRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {


    @Bean
    public CommandLineRunner initBusData(BusRepository busRepository) {
        return args -> {
            if (busRepository.count() == 0) {
                System.out.println("🚀 Inserting initial bus data...");

                List<Bus> buses = new ArrayList<>();

                for (int i = 1; i <= 5; i++) {
                    buses.add(new Bus("CH-MDU-" + i, "Chennai", "Madurai",
                            LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(i).plusHours(6),
                            40, 300.0));

                    buses.add(new Bus("MDU-CH-" + i, "Madurai", "Chennai",
                            LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(i).plusHours(6),
                            40, 300.0));

                    buses.add(new Bus("CH-CBE-" + i, "Chennai", "Coimbatore",
                            LocalDateTime.now().plusDays(i), LocalDateTime.now().plusDays(i).plusHours(7),
                            40, 350.0));
                }

                busRepository.saveAll(buses);
                System.out.println("✅ Inserted " + buses.size() + " buses into MySQL.");
            } else {
                System.out.println("ℹ️ Bus data already exists. Skipping insert.");
            }
        };
    }
}
