package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user_roles")
public class UserRoles {

    @Id
    @Column(name = "userRoleid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleId;
    @Column(name = "userRole")
    private String role;
    @Column(name = "status")
    private String status;
}
