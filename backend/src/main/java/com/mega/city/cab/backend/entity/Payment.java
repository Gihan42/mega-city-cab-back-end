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
@Builder
@Entity(name = "payment")
public class Payment {
    @Id
    @Column(name = "paymentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;
    @Column(name = "bookingId")
    private Long bookingId;
    @Column(name = "amount")
    private double amount;
    @Column(name = "date")
    private Date date;
    @Column(name = "paymentMethod")
    private String paymentMethod;
    @Column(name = "currency")
    private String currency;
    @Column(name = "customerId")
    private Long customerId;
    @Column(name = "vehicleId")
    private Long vehicleId;
    @Column(name = "status")
    private String status;
}
