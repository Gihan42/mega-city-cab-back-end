package com.mega.city.cab.backend.controller;

import com.mega.city.cab.backend.dto.EmailDetailsDto;
import com.mega.city.cab.backend.dto.PaymentDto;
import com.mega.city.cab.backend.service.EmailService;
import com.mega.city.cab.backend.util.response.StandardResponse;
import com.mega.city.cab.backend.util.response.StripeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/email")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class EmailSendController {

    @Autowired
    EmailService emailService;

    @PostMapping(path = "/sendMail")
    public ResponseEntity<StandardResponse> sendMail(@RequestBody EmailDetailsDto dto,
                                                          @RequestAttribute String type) {
        SimpleMailMessage simpleMailMessage = emailService.sendBookingConfirmationEmail(dto, type);
        return new ResponseEntity<>(
                new StandardResponse(200,"Send Email Successfully ",simpleMailMessage),
                HttpStatus.OK
        );
    }
}
