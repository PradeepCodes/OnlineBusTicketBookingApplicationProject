package com.example.OnlineBusTicketBookingApplication.Service;

import com.example.OnlineBusTicketBookingApplication.Entity.Payment;

import java.util.Optional;

public interface PaymentService {
    Payment savePayment(Payment payment);
    Optional<Payment> getPaymentById(Long id);
}
