package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.entity.AfterSales;
import com.ideu.demo.server08.service.AfterSalesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/afterSales")
public class AfterSalesController {

    private final AfterSalesService afterSalesService;

    @Autowired
    public AfterSalesController(AfterSalesService afterSalesService) {
        this.afterSalesService = afterSalesService;
    }

    @PostMapping("add")
    @Operation(summary = "新增售后服务")
    public void addOrderService(@RequestBody AfterSales afterSales) {
        Long userId=21l;
        afterSales.setUserId(userId);
        afterSalesService.addOrderService(afterSales);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询售后服务")
    public AfterSales getOrderServiceById(@PathVariable Long id) {
        return afterSalesService.getOrderServiceById(id);
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "根据订单ID查询售后服务")
    public List<AfterSales> getOrderServicesByOrderId(@PathVariable Long orderId) {
        return afterSalesService.getOrderServicesByOrderId(orderId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新售后服务")
    public void updateOrderService(@PathVariable Long id, @RequestBody AfterSales afterSales) {
        afterSales.setId(id);
        afterSalesService.updateOrderService(afterSales);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "根据ID删除售后服务")
    public void deleteOrderServiceById(@PathVariable Long id) {
        afterSalesService.deleteOrderServiceById(id);
    }

    @GetMapping
    @Operation(summary = "查询所有售后服务")
    public List<AfterSales> getAllOrderServices() {
        return afterSalesService.getAllOrderServices();
    }
}
