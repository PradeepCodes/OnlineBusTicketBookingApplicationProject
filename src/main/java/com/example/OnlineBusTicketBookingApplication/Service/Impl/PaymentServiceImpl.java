package com.example.OnlineBusTicketBookingApplication.Service.Impl;

import com.example.OnlineBusTicketBookingApplication.Entity.Payment;
import com.example.OnlineBusTicketBookingApplication.Repository.PaymentRepository;
import com.example.OnlineBusTicketBookingApplication.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
}

