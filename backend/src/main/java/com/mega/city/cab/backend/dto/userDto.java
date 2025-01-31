package com.mega.city.cab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class userDto {
    private Long id;
    private String username;
    private String password;
    private String contactNumber;
    private String email;
    private String address;
    private String nic;
    private String status;
    private String role;
}

