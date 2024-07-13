package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.entity.KpiMetric;
import com.ideu.demo.server08.mapper.KpiMetricMapper;
import com.ideu.demo.server08.service.KpiMetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional // 添加事务注解
public class KpiMetricServiceImpl implements KpiMetricService {

    @Autowired
    private KpiMetricMapper kpiMetricMapper;

    @Override
    public List<KpiMetric> findAll() {
        return kpiMetricMapper.findAll();
    }


    @Override
    public KpiMetric save(KpiMetric kpiMetric) {
        kpiMetricMapper.save(kpiMetric);
        return kpiMetric;
    }

    @Override
    public void deleteById(Long id) {
        kpiMetricMapper.delete(id);
    }



}
