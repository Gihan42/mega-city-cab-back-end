package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user_role_details")
public class UserRoleDetails {

    @Id
    @Column(name = "userRoleDetailsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    private Long userRoleDetailsId;
    @Column(name = "userRole")
    @NonNull
    private Long userRole;
    @Column(name = "userId")
    @NonNull
    private Long userId;
    @Column(name = "status")
    @NonNull
    private String status;
}
