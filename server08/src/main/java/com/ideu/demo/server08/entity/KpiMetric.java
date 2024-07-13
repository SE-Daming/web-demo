package com.ideu.demo.server08.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Entity
@Data
@Table(name = "kpi_metrics")
public class KpiMetric
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "metric_name")
    private String metricName;

    @Column(name = "value")
    private BigDecimal value;

}

