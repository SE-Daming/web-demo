package com.ideu.demo.server08.controller;

import com.ideu.demo.server08.common.Result;
import com.ideu.demo.server08.entity.Payment;
import com.ideu.demo.server08.service.PaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/add")
    @Operation(summary ="添加账单")
    public Result createPayment(@RequestBody Payment payment) {
        paymentService.createPayment(payment);
        payment.setPaymentMethod("支付宝");
        if (payment.getPaymentId() != null) {
            return Result.success("支付成功");
        } else {
            return Result.error("支付失败");
        }
    }

    @GetMapping("/selectPaymentById/{paymentId}")
    @Operation(summary ="根据支付编号查询支付信息")
    public Result getPaymentById(@PathVariable Long paymentId) {
        Payment payment = paymentService.getPaymentById(paymentId);
        if (payment != null) {
            return Result.success(payment);
        } else {
            return Result.error("支付记录不存在或查询失败");
        }
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary ="根据订单号查询支付信息")
    public Result getPaymentsByOrderId(@PathVariable Long orderId) {
        List<Payment> payments = paymentService.getPaymentByOrderId(orderId);
        if (!payments.isEmpty()) {
            return Result.success(payments);
        } else {
            return Result.error("订单不存在或无支付记录");
        }
    }

    @PutMapping("/update")
    @Operation(summary ="更新支付记录")
    public Result updatePayment(@RequestBody Payment payment) {
        paymentService.updatePayment(payment);
        if (paymentService.getPaymentById(payment.getPaymentId()) != null) {
            return Result.success("支付记录更新成功");
        } else {
            return Result.error("支付记录更新失败或支付记录不存在");
        }
    }

    @DeleteMapping("/deletePaymentById/{paymentId}")
    @Operation(summary ="根据支付编号删除支付记录")
    public Result deletePayment(@PathVariable Long paymentId) {
        paymentService.deletePayment(paymentId);
        if (paymentService.getPaymentById(paymentId) == null) {
            return Result.success("支付记录删除成功");
        } else {
            return Result.error("支付记录删除失败或支付记录仍存在");
        }
    }

    // 结算服务接口和计算平台抽成接口未提供具体实现和描述，暂不添加到 Swagger 文档中。

}
