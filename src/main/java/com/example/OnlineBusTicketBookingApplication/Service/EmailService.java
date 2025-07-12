package com.example.OnlineBusTicketBookingApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendResetPasswordEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("🔐 Reset Your Password");
        message.setText("Hi,\n\nClick the link below to reset your password:\n" + resetLink + "\n\nIf you didn’t request this, ignore this email.");

        mailSender.send(message);
    }
    public void sendBookingConfirmationEmail(String toEmail, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);
    }
}
