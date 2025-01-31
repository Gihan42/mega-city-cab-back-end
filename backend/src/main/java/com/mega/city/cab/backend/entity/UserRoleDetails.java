package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "user_role_details")
public class UserRoleDetails {

    @Id
    @Column(name = "userRoleDetailsId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userRoleDetailsId;
    @Column(name = "userRole")
    private Long userRole;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "status")
    private String status;
}
