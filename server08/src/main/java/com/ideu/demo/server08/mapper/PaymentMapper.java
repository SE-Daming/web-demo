package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.Payment;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PaymentMapper {

    @Insert("INSERT INTO payments(order_id, user_id, amount, payment_time, payment_method) " +
            "VALUES(#{orderId}, #{userId}, #{amount}, #{paymentTime}, #{paymentMethod})")
    void insertPayment(Payment payment);

    @Select("SELECT * FROM payments WHERE id = #{paymentId}")
    Payment selectPaymentById(Long paymentId);

    @Select("SELECT * FROM payments WHERE order_id = #{orderId}")
    List<Payment> selectPaymentByOrderId(Long orderId);

    @Update("UPDATE payments SET order_id = #{orderId}, user_id = #{userId}, amount = #{amount}, " +
            "payment_time = #{paymentTime}, payment_method = #{paymentMethod} WHERE id = #{id}")
    void updatePayment(Payment payment);

    @Delete("DELETE FROM payments WHERE id = #{paymentId}")
    void deletePaymentById(Long paymentId);



    @Select("SELECT * FROM payments")
    List<Payment> selectAllPayments();

    @Select("SELECT * FROM payments WHERE order_id = #{id}")
    Payment getByOrderId(String id);
}
