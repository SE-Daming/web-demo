package com.ideu.demo.server08.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(name = "order_id")
    private String orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "amount")
    private String amount;

    @Column(name = "payment_time")
    private LocalDateTime paymentTime;

    @Column(name = "payment_method")
    private String paymentMethod;


}
