package com.ideu.demo.server08.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "commission_rates")
@Data
public class ComRate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_type")
    private String oderType;

    @Column(name = "commission_rate")
    private BigDecimal commissionRate;

}
