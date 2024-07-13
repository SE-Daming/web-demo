package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.common.Result;
import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.entity.Engineer;
import com.ideu.demo.server08.service.EngineerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/engineers")
public class EngineerController {

    @Autowired
    private EngineerService engineerService;

    @GetMapping("/pageQuery")
    @Operation(summary ="分页查询工程师")
    public PageResult pageQuery(@RequestParam String pageNum,
                                @RequestParam String pageSize,
                                @RequestParam(required = false) String  name,
                                @RequestParam(required = false) String serviceArea) {
        return engineerService.pageQuery(pageNum, pageSize, serviceArea, name);
    }

    @PostMapping("update")
    @Operation(summary = "工程师更新")
    public Result update(@RequestBody Engineer engineer) {
        engineerService.update(engineer);
        return Result.success();
    }

    @GetMapping("/findAll")
    @Operation(summary ="查询所有工程师")
    public Result<List<Engineer>> findAll() {
        return Result.success(engineerService.findAll());
    }

    @GetMapping("/getById/{id}")
    @Operation(summary ="根据ID查询工程师")
    public Result<Optional<Engineer>> findById(@PathVariable Long id) {
        return Result.success(engineerService.findById(id));
    }

    @PostMapping("/add")
    @Operation(summary ="新增工程师")
    public Result add(@RequestBody Engineer engineer) {
        try {
            engineerService.save(engineer);
            return Result.success("新增成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("新增失败");
        }
    }

    @DeleteMapping("/deleteById/{id}")
    @Operation(summary ="根据ID删除工程师")
    public Result deleteById(@PathVariable Long id) {
        try {
            engineerService.deleteById(id);
            return Result.success("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("删除失败");
        }
    }

//    @PutMapping("/{id}/approve-qualification")
//     @Operation(summary ="审核工程师资格")
//    public Result approveQualification(@PathVariable Long id, @RequestParam Boolean approved) {
//        try {
//            engineerService.approveQualification(id, approved);
//            return Result.success("资格审核成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error("资格审核失败");
//        }
//    }
//
//    @PutMapping("/{id}/update-performance-rating")
//     @Operation(summary ="更新工程师绩效评级")
//    public Result updatePerformanceRating(@PathVariable Long id, @RequestBody Integer performanceRating) {
//        try {
//            engineerService.updatePerformanceRating(id, performanceRating);
//            return Result.success("绩效评级更新成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error("绩效评级更新失败");
//        }
//    }
//
//    @PutMapping("/{id}/update-service-area")
//     @Operation(summary ="更新工程师服务区域")
//    public Result updateServiceArea(@PathVariable Long id, @RequestBody String area) {
//        try {
//            engineerService.updateServiceArea(id, area);
//            return Result.success("服务区域更新成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error("服务区域更新失败");
//        }
//    }
//
//    @PutMapping("/{id}/update-appliance-types")
//     @Operation(summary ="更新工程师设备类型")
//    public Result updateApplianceTypes(@PathVariable Long id, @RequestParam String types) {
//        try {
//            engineerService.updateApplianceTypes(id, types);
//            return Result.success("设备类型更新成功");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Result.error("设备类型更新失败");
//        }
//    }
}
