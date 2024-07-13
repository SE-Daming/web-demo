package com.ideu.demo.server08.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "engineers")
public class Engineer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "service_area")
    private String serviceArea;

    @Column(name = "performance_rating")
    private String performanceRating;

    @Column(name = "types")
    private String types; // Assuming applianceTypes is stored as JSON string


}
