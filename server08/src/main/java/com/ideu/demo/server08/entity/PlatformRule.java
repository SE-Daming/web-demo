package com.ideu.demo.server08.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "platform_rules")
public class PlatformRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    // Getters and setters
}
