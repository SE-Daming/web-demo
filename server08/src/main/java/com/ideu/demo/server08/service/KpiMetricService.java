package com.ideu.demo.server08.service;

import com.ideu.demo.server08.entity.KpiMetric;

import java.util.List;

public interface KpiMetricService {
    List<KpiMetric> findAll();


    KpiMetric save(KpiMetric kpiMetric);

    void deleteById(Long id);

}
