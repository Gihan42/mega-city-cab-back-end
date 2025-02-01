package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "comments")
public class Comments {
    @Id
    @Column(name = "commentId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;
    @Column(name = "comment")
    private String comment;
    @Column(name = "userId")
    private Long userId;
    @Column(name = "date")
    private Date date;
    @Column(name = "status")
    private String status;

}
