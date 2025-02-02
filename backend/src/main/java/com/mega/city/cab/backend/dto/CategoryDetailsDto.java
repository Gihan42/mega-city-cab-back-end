package com.mega.city.cab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDetailsDto {
    private Long categoryDetailsId;
    private Long categoryId;
    private Long vehicleId;
    private String status;
}
