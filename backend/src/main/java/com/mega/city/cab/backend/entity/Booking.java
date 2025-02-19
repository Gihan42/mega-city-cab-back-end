package com.mega.city.cab.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "booking")
public class Booking {
    @Id
    @Column(name = "bookingId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;
    @NonNull
    @Column(name = "cutomerId")
    private Long customerId;
    @NonNull
    @Column(name = "vehicleId")
    private Long vehicleId;
    @NonNull
    @Column(name = "driverId")
    private Long driverId;
    @NonNull
    @Column(name = "pickUpLocation")
    private String pickUpLocation;
    @NonNull
    @Column(name = "dropLocation")
    private String dropLocation;
    @NonNull
    @Column(name = "hours")
    private String hours;
    @NonNull
    @Column(name = "totalKm")
    private double totalKm;
    @NonNull
    @Column(name = "bookingDateTime")
    private Date bookingDateTime;
    @NonNull
    @Column(name = "amount")
    private double amount;
    @Column(name = "estimatedBookingDateTime")
    private Date estimatedBookingDateTime;
    @NonNull
    @Column(name = "status")
    private String status;
}
