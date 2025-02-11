package com.mega.city.cab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmailDetailsDto {
    private long customerId;
    private long bookingId;
    private String customerName;
    private String customerContact;
    private String pickUpLocation;
    private String dropLocation;
    private Date  bookingDate;
    private String driverEmail;
}
