package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "categoryDetails")
public class CategoryDetails {

    @Id
    @Column(name = "categoryDetailsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryDetailsId;
    @Column(name = "categoryId")
    private Long categoryId;
    @Column(name = "vehicleId")
    private Long vehicleId;
    @Column(name = "status")
    private String status;

}
