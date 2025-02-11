package com.mega.city.cab.backend.service.impl;

import com.mega.city.cab.backend.dto.EmailDetailsDto;
import com.mega.city.cab.backend.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public SimpleMailMessage sendBookingConfirmationEmail(EmailDetailsDto dto, String type) {
        if (!type.equals("User")) {
            throw new BadCredentialsException("dont have permission");
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("apismtp@mailtrap.io"); // Username එකට හරිල update කරන්න
        message.setTo(dto.getDriverEmail());
        message.setSubject("New Booking Confirmation");
        message.setText(
                "Dear Driver,\n\n" +
                        "You have a new booking with the following details:\n\n" +
                        "Customer Name: " + dto.getCustomerName() + "\n" +
                        "Pickup Location: " + dto.getPickUpLocation() + "\n" +
                        "Drop Location: " + dto.getDropLocation() + "\n" +
                        "Booking Id: " + dto.getBookingId() + "\n" +
                        "Customer contact: " + dto.getCustomerContact() + "\n" +
                        "Booking Time: " + dto.getBookingDate() + "\n\n" +
                        "Thank you!"
        );
        javaMailSender.send(message);
        return message;
    }

}
