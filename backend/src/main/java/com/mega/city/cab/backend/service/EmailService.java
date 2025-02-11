package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.EmailDetailsDto;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

    SimpleMailMessage sendBookingConfirmationEmail(EmailDetailsDto dto, String type);
}
