package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Column(name = "cutomerId")
    private Long customerId;
    @Column(name = "vehicleId")
    private Long vehicleId;
    @Column(name = "pickUpLocation")
    private String pickUpLocation;
    @Column(name = "dropLocation")
    private String dropLocation;
    @Column(name = "hours")
    private String hours;
    @Column(name = "totalKm")
    private double totalKm;
    @Column(name = "bookingDateTime")
    private Date bookingDateTime;
    @Column(name = "amount")
    private double amount;
    @Column(name = "status")
    private String status;
}
