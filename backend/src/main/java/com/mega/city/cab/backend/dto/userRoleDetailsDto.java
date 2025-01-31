package com.mega.city.cab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class userRoleDetailsDto {
    private Long id;
    private Long userRole;
    private Long userId;
    private String status;
}
