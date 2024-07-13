package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.common.Result;
import com.ideu.demo.server08.entity.PlatformRule;
import com.ideu.demo.server08.service.PlatformRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/home/platform-rules")
public class PlatformRuleController {

    @Autowired
    private PlatformRuleService platformRuleService;

    @GetMapping("getAll")
    @Operation(summary ="获取所有平台规则")
    public Result findAll() {
        List<PlatformRule> platformRules = platformRuleService.findAll();
        return Result.success(platformRules);
    }

    @PostMapping("add")
    @Operation(summary ="添加平台规则")
    public Result save(@RequestBody PlatformRule platformRule) {
        try {
            platformRuleService.save(platformRule);
            return Result.success("平台规则保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("平台规则保存失败!");
        }
    }

    @DeleteMapping("deleteById/{id}")
    @Operation(summary ="根据ID删除平台规则")
    public Result deleteById(@PathVariable Long id) {
        platformRuleService.deleteById(id);
        return Result.success("平台规则删除成功!");
    }

    /*
    @PutMapping("update/{id}")
     @Operation(summary ="根据ID更新平台规则")
    public Result update(@PathVariable Long id, @RequestBody PlatformRule platformRule) {
        platformRule.setId(id);
        try {
            platformRuleService.save(platformRule);
            return Result.success("平台规则更新成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("平台规则更新失败!");
        }
    }
    */

}
