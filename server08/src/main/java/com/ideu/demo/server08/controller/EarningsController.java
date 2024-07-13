package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.entity.Earnings;
import com.ideu.demo.server08.service.EarningsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/earnings")
public class EarningsController {

    private final EarningsService earningsService;

    @Autowired
    public EarningsController(EarningsService earningsService) {
        this.earningsService = earningsService;
    }

    @GetMapping("/{id}")
    @Operation(summary ="根据ID查询收益记录")
    public Optional<Earnings> getEarningsById(@PathVariable Long id) {
        return earningsService.getEarningsById(id);
    }

    @GetMapping("/findAll")
    @Operation(summary ="查询所有收益记录")
    public PageResult<Earnings> findAll() {
        List<Earnings> earningsList = earningsService.findAll();
        return new PageResult<>(earningsList, earningsList.size());
    }

    @GetMapping("/findAllPaged")
    @Operation(summary ="分页查询收益记录")
    public PageResult<Earnings> findAllPaged(@RequestParam(defaultValue = "1") int pageNo,
                                             @RequestParam(defaultValue = "10") int pageSize) {
        List<Earnings> earningsList = earningsService.findAllPaged(pageNo, pageSize);
        long total = earningsService.countAll(); // 获取总记录数
        return new PageResult<>(earningsList, total, pageNo, pageSize);
    }

    @PostMapping("/save")
    @Operation(summary ="插入收益记录")
    public Earnings saveEarnings(@RequestBody Earnings earnings) {
        return earningsService.saveEarnings(earnings);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary ="根据ID删除收益记录")
    public void deleteEarnings(@PathVariable Long id) {
        earningsService.deleteEarnings(id);
    }

    @DeleteMapping("/deleteBatch")
    @Operation(summary ="批量删除收益记录")
    public void deleteBatch(@RequestParam List<Long> ids) {
        earningsService.deleteBatch(ids);
    }
}
