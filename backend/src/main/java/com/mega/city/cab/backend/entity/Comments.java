package com.mega.city.cab.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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
    @NonNull
    @Column(name = "comment")
    private String comment;
    @NonNull
    @Column(name = "userId")
    private Long userId;
    @NonNull
    @Column(name = "date")
    private Date date;
    @NonNull
    @Column(name = "status")
    private String status;

}
