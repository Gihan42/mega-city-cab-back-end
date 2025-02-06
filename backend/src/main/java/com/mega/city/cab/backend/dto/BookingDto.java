package com.mega.city.cab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookingDto {
    private Long bookingId;
    private Long customerId;
    private Long vehicleId;
    private Long driverId;
    private String pickUpLocation;
    private String dropLocation;
    private String hours;
    private double totalKm;
    private Date bookingDateTime;
    private double amount;
    private String status;

}
