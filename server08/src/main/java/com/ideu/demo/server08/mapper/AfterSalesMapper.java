package com.ideu.demo.server08.mapper;

import com.ideu.demo.server08.entity.AfterSales;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AfterSalesMapper {

    @Insert("INSERT INTO after_sales (order_id, user_id, service_type, service_description, status, created_time) " +
            "VALUES (#{orderId}, #{userId}, #{serviceType}, #{serviceDescription}, #{status}, #{createdTime})")
    void insertOrderService(AfterSales afterSales);

    @Select("SELECT * FROM after_sales WHERE id = #{id}")
    AfterSales getOrderServiceById(Long id);

    @Select("SELECT * FROM after_sales WHERE order_id = #{orderId}")
    List<AfterSales> getOrderServicesByOrderId(Long orderId);

    @Update("UPDATE after_sales SET " +
            "order_id = #{orderId}, " +
            "user_id = #{userId}, " +
            "service_type = #{serviceType}, " +
            "service_description = #{serviceDescription}, " +
            "status = #{status}, " +
            "created_time = #{createdTime} " +
            "WHERE id = #{id}")
    void updateOrderService(AfterSales afterSales);

    @Delete("DELETE FROM after_sales WHERE id = #{id}")
    void deleteOrderServiceById(Long id);

    @Select("SELECT * FROM after_sales")
    List<AfterSales> getAllOrderServices();
}
