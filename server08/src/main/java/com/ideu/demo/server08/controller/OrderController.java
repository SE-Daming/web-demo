package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.common.BaseContext;
import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.common.Result;
import com.ideu.demo.server08.entity.*;
import com.ideu.demo.server08.service.OrderService;
import com.ideu.demo.server08.service.PaymentService;
import com.ideu.demo.server08.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    PaymentService paymentService;
    @Autowired
    UserService userService;

    @PostMapping("/add")
    @Operation(summary ="添加订单")
    public Result addOrder(@RequestBody Order order,HttpServletRequest request) {
        String k = request.getHeader("Authorization");
        Long userId= Long.valueOf(redisTemplate.opsForValue().get(k));
        if(userId == null){
            userId=21l;
        }
        order.setUserId(userId);
        order.setCreatedTime(LocalDateTime.now());
        order.setUpdatedTime(LocalDateTime.now());
        order.setOrderStatus("0");
        order.setId(UUID.randomUUID().toString());

        Payment payment=new Payment();
        payment.setOrderId(order.getId());
        payment.setUserId(userId);
        payment.setAmount(order.getPrice());
        payment.setPaymentTime(LocalDateTime.now());
        paymentService.createPayment(payment);

        orderService.addOrder(order);
            return Result.success("订单添加成功");

    }

    @GetMapping("/pageQuery")
    @Operation(summary ="分页查询订单")
    public Result<PageResult> pageQuery(@RequestParam Integer pageNum,
                                        @RequestParam Integer pageSize,
                                        @RequestParam(required = false) Long orderId,
                                        @RequestParam(required = false) String contactPhone,
                                        @RequestParam(required = false) LocalDate beginTime,
                                        @RequestParam(required = false) LocalDate endTime,
                                        HttpServletRequest request) {
        String k = request.getHeader("Authorization");
        Long userId= Long.valueOf(redisTemplate.opsForValue().get(k));
        String flag=null;
        User user=userService.getById(userId);
        if(user.getRole().equals("1")){
            flag="true";
        }
        return Result.success(orderService.pageQuery(pageNum, pageSize, orderId, contactPhone, beginTime, endTime,flag,user.getRegion()));
    }

    @GetMapping("/getOrderById/{orderId}")
    @Operation(summary ="根据订单ID查询订单详情")
    public Result getOrderById(@PathVariable String orderId) {
        Order order = orderService.getOrderById(orderId);
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error("订单不存在或查询失败");
        }
    }

    @GetMapping("/getAllOrders")
    @Operation(summary ="查询所有订单")
    public Result getAllOrders() {
        List<Order> orders = orderService.getAll();
        return Result.success(orders);
    }

    @GetMapping("/getByUserId")
    @Operation(summary ="根据用户ID查询其所有订单")
    public Result getOrdersByUserId(HttpServletRequest request) {
        String k = request.getHeader("Authorization");
        Long userId= Long.valueOf(redisTemplate.opsForValue().get(k));
        if (userId == null) {
            userId = 21l;
        }
        return Result.success(orderService.getOrdersByUserId(userId));
    }
    @GetMapping("getEmpOrderById")
    @Operation(summary ="根据ID查询其所有订单")
    public Result getEngineerOrders(HttpServletRequest request){
        String k = request.getHeader("Authorization");
        Long userId= Long.valueOf(redisTemplate.opsForValue().get(k));
        if (userId == null) {
            userId = 11l;
        }
        List<EmpOrderVo>vos=orderService.getEmpOrders(userId.toString());
        return Result.success(vos);
    }

    @PutMapping("/update")
    @Operation(summary ="更新订单信息")
    public Result updateOrder(@RequestBody Order order) {
        orderService.updateOrder(order);
        return Result.success("订单更新成功");
    }

    @PutMapping("/assignEngineer")
    @Operation(summary ="分配工程师")
    public Result assignEngineer(@RequestBody Order order) {
        orderService.assignedEngineer(order);
        return Result.success("工程师分配成功");
    }

    @DeleteMapping("/deleteOrderById/{orderId}")
    @Operation(summary ="根据订单ID删除订单")
    public Result deleteOrderById(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return Result.success("订单删除成功");
    }

    @GetMapping("/viewOrder/{area}")
    @Operation(summary ="根据地区查询订单详情")
    public Result viewOrder(@PathVariable String area) {
        Order order = orderService.viewOrder(area);
        if (order != null) {
            return Result.success(order);
        } else {
            return Result.error("订单不存在或查询失败");
        }
    }
    @GetMapping("/getOrderByEngineer/{assignedEngineer}")
    @Operation(summary ="根据工程师ID查询订单详情")
    public List<Order> getEngineerOrders(@PathVariable Integer engineerId)
    {
        return (List<Order>) orderService.getOrderByEngineer(engineerId);
    }

    @GetMapping("getTypes")
    @Operation(summary = "获取家電类型 暫時不要")
    @Deprecated
    public Result getTypes()
    {
        return Result.success(orderService.getTypes());
    }
    @Autowired
    StringRedisTemplate redisTemplate;

    @PostMapping("receiveOrder")
    @Operation(summary = "接收订单")
    public Result receiveOrder(@RequestBody Order order, HttpServletRequest request)
    {
        String k = request.getHeader("Authorization");
        Long userId= Long.valueOf(redisTemplate.opsForValue().get(k));
        if (userId == null) {
            userId = 21l;
        }
        return Result.success(orderService.receiveOrder(order.getId(), userId));//TODO 模拟分配的工程师、后期拦截器获得
    }

    @PostMapping("finishOrder")
    @Operation(summary = "完成订单")
    public Result finishOrder(@RequestBody Order order,HttpServletRequest request)
    {
        String k = request.getHeader("Authorization");
        Long userId= Long.valueOf(redisTemplate.opsForValue().get(k));
        if (userId == null) {
            userId = 12l;
        }
        orderService.finishOrder(order,userId);
        return Result.success("订单完成成功");
    }
    @PostMapping("/statistics2/{startDate}/{endDate}")
    public List<OrderStatistic> getOrderStatistics(
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        // Convert LocalDate to java.sql.Date
        java.sql.Date sqlStartDate = java.sql.Date.valueOf(startDate);
        java.sql.Date sqlEndDate = java.sql.Date.valueOf(endDate);

        // 调用 Service 层获取订单统计数据
        return orderService.getOrderStatistics(sqlStartDate, sqlEndDate);
    }

    @PostMapping("/statistics/{startDate}/{endDate}")
    public OrderStatisticsVo getOrderStatistics2(
            @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
        OrderStatisticsVo vos=orderService.getOrderStatistics2(startDate, endDate);
        return  vos;
    }

    @PostMapping("cancel/{id}")
    @Operation(summary = "取消订单")
    public Result cancelOrder(@PathVariable("id") String id)
    {

        orderService.cancel(id);
        return Result.success("订单取消成功");
    }
}
