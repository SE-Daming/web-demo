package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.entity.Engineer;
import com.ideu.demo.server08.entity.Order;
import com.ideu.demo.server08.entity.Payment;
import com.ideu.demo.server08.mapper.EngineerMapper;
import com.ideu.demo.server08.mapper.OrderMapper;
import com.ideu.demo.server08.mapper.PaymentMapper;
import com.ideu.demo.server08.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService
{

    @Autowired
    private PaymentMapper paymentMapper;


    
    @Override
    public void createPayment(Payment payment)
    {
        paymentMapper.insertPayment(payment);
    }

    @Override
    public Payment getPaymentById(Long paymentId)
    {
        return paymentMapper.selectPaymentById(paymentId);
    }

    @Override
    public List<Payment> getPaymentByOrderId(Long orderId)
    {
        return paymentMapper.selectPaymentByOrderId(orderId);
    }

    @Override
    public void updatePayment(Payment payment)
    {
        paymentMapper.updatePayment(payment);
    }

    @Override
    public void deletePayment(Long paymentId)
    {
        paymentMapper.deletePaymentById(paymentId);
    }







}

