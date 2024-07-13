package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.common.BaseContext;
import com.ideu.demo.server08.common.Result;
import com.ideu.demo.server08.entity.UserFeedback;
import com.ideu.demo.server08.service.UserFeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("userFeedbacks")
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @Operation(summary = "获取所有用户反馈")
    @GetMapping("getAll")
    public Result findAll() {
        List<UserFeedback> userFeedbacks = userFeedbackService.findAll();
        return Result.success(userFeedbacks);
    }

    @Operation(summary ="添加用户反馈")
    @PostMapping("add")
    public Result save(@RequestBody UserFeedback userFeedback) {
        Long userId= 21l;//BaseContext.getCurrentId();
        userFeedback.setUserId(userId);
        try {
            userFeedbackService.save(userFeedback);
            return Result.success("用户反馈保存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("用户反馈保存失败!");
        }
    }

    @Operation(summary ="根据ID删除用户反馈")
    @DeleteMapping("deleteById/{id}")
    public Result deleteById(@PathVariable Long id) {
        userFeedbackService.deleteById(id);
        return Result.success("用户反馈删除成功!");
    }

}
