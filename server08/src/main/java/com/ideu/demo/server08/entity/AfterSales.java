package com.ideu.demo.server08.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "after_sales")
public class AfterSales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "service_type")
    private String serviceType;
    @Column(name = "service_description")
    private String serviceDescription;
    @Column(name = "status")
    private String status;
    @Column(name = "created_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;



}
