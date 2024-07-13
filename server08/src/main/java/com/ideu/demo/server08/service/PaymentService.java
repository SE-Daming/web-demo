package com.ideu.demo.server08.service;

import com.ideu.demo.server08.entity.Payment;

import java.util.List;

public interface PaymentService {

    void createPayment(Payment payment);

    Payment getPaymentById(Long paymentId);

    List<Payment> getPaymentByOrderId(Long orderId);

    void updatePayment(Payment payment);

    void deletePayment(Long paymentId);




}

