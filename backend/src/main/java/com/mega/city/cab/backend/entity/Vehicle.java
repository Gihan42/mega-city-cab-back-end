package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "vehicle")
public class Vehicle {

    @Id
    @Column(name = "vehicleId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehicleId;
    @Column(name="plateNumber")
    private String plateNumber;
    @Column(name="passengerCount")
    private int passengerCount;
    @Column(name="pricePerKm")
    private double pricePerKm;
    @Column(name = "vehicleModel")
    private String vehicleModel;
    @Column(name = "status")
    private String status;
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;


}
