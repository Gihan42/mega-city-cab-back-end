package com.mega.city.cab.backend.service;

import com.mega.city.cab.backend.dto.EmailDetailsDto;
import com.mega.city.cab.backend.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.BadCredentialsException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailServiceImpl emailService;

    private EmailDetailsDto emailDetailsDto;

    @BeforeEach
    void setUp() throws ParseException {
        // Initialize EmailDetailsDto
        emailDetailsDto = new EmailDetailsDto();
        emailDetailsDto.setDriverEmail("driver@example.com");
        emailDetailsDto.setCustomerName("John Doe");
        emailDetailsDto.setPickUpLocation("Location A");
        emailDetailsDto.setDropLocation("Location B");
        emailDetailsDto.setBookingId(1l);
        emailDetailsDto.setCustomerContact("1234567890");

        // Convert String to Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a");
        Date bookingDate = dateFormat.parse("2023-10-01 10:00 AM");
        emailDetailsDto.setBookingDate(bookingDate);
    }

    @Test
    void testSendBookingConfirmationEmail_Success() {
        // Arrange
        String type = "User";

        // Act
        SimpleMailMessage result = emailService.sendBookingConfirmationEmail(emailDetailsDto, type);

        // Assert
        assertNotNull(result);
        assertEquals("apismtp@mailtrap.io", result.getFrom());
        assertEquals("driver@example.com", result.getTo()[0]);
        assertEquals("New Booking Confirmation", result.getSubject());
        assertTrue(result.getText().contains("John Doe"));
        assertTrue(result.getText().contains("Location A"));
        assertTrue(result.getText().contains("12345"));

        // Verify that javaMailSender.send() was called
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void testSendBookingConfirmationEmail_ThrowsBadCredentialsException() {
        // Arrange
        String type = "Admin";

        // Act & Assert
        Exception exception = assertThrows(BadCredentialsException.class, () -> {
            emailService.sendBookingConfirmationEmail(emailDetailsDto, type);
        });

        assertEquals("dont have permission", exception.getMessage());

        // Verify that javaMailSender.send() was not called
        verify(javaMailSender, never()).send(any(SimpleMailMessage.class));
    }
}