package com.mega.city.cab.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentsDto {
    private Long commentId;
    private String comment;
    private Long userId;
    private Date date;
    private String status;
}
