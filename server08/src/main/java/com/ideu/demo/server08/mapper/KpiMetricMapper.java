package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.KpiMetric;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KpiMetricMapper {


    @Insert("INSERT INTO kpi_metrics(metric_name, value) " +
            "VALUES (#{metricName}, #{value})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void save(KpiMetric kpiMetric);


    @Delete("DELETE FROM kpi_metrics WHERE kpi_id = #{kpiId}")
    void delete(Long id);

    @Select("SELECT * FROM kpi_metrics")
    List<KpiMetric> findAll();

}
