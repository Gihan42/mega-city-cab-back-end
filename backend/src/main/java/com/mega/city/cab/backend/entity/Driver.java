package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "driver")
public class Driver {

    @Id
    @Column(name = "driverId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    @Column(name = "name")
    private String name;
    @Column(name = "age")
    private int age;
    @Column(name = "email")
    private String email;
    @Column(name = "licenseNumber")
    private String licenseNumber;
    @Column(name = "contactNumber")
    private String contactNumber;
    @Column(name = "nic")
    private String nic;
    @Column(name = "address")
    private String address;
    @Column(name = "status")
    private String status;

}
