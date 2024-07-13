package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.common.Result;
import com.ideu.demo.server08.entity.KpiMetric;
import com.ideu.demo.server08.service.KpiMetricService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/kpi-metrics")
public class KpiMetricController {

    @Autowired
    private KpiMetricService kpiMetricService;

    @GetMapping("/findAll")
    @Operation(summary ="查询所有KPI规则")
    public Result findAll() {
        return Result.success(kpiMetricService.findAll());
    }

    @PostMapping("/add")
    @Operation(summary ="保存KPI规则")
    public Result save(@RequestBody KpiMetric kpiMetric) {
        try {
            kpiMetricService.save(kpiMetric);
            return Result.success("KPI规则保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("KPI规则保存失败!");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    @ApiOperation("根据ID删除KPI规则")
    public Result deleteById(@PathVariable Long id)
    {
        kpiMetricService.deleteById(id);
        return Result.success("KPI规则删除成功!");
    }
}
