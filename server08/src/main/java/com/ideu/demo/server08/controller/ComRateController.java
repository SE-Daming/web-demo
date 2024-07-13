package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.entity.ComRate;
import com.ideu.demo.server08.service.ComRateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/comRates")
public class ComRateController {

    @Autowired
    private ComRateService comRateService;

    @GetMapping("/findAll")
    @Operation(summary ="查询所有抽成比例")
    public List<ComRate> findAll() {
        return comRateService.findAll();
    }

    @PostMapping("/save")
    @Operation(summary ="保存抽成比例")
    public ComRate save(@RequestBody ComRate comRate) {
        return comRateService.save(comRate);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary ="根据ID删除抽成比例")
    public void deleteById(@PathVariable Long id) {
        comRateService.deleteById(id);
    }
}
