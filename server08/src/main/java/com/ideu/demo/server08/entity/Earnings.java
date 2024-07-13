package com.ideu.demo.server08.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = "earnings")
@Data
public class Earnings
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "platform_fee")
    private BigDecimal platformFee;

    @Column(name = "engineer_fee")
    private BigDecimal engineerFee;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "earning_time")
    private LocalDateTime earningTime;
}
