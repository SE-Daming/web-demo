package com.ideu.demo.server08.mapper;

import com.github.pagehelper.Page;
import com.ideu.demo.server08.entity.Order;
import com.ideu.demo.server08.entity.OrderStatistic;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders(id,user_id, type, detail_id, description, contact_phone, user_address, " +
            " area, order_status, priority, finish_pic,price) " +
            "VALUES(#{id},#{userId}, #{type},#{detailId} , #{description}, #{contactPhone}, #{userAddress}, " +
            " #{area}, #{orderStatus}, #{priority},  #{finishPic},#{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void addOrder(Order order);

    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order getOrderById(String id);

    @Select("SELECT * FROM orders WHERE user_id = #{userId}")
    List<Order> getOrdersByUserId(Long userId);

    @Update("UPDATE orders SET " +
            "user_id = #{userId}," +
            " type = #{type}, " +
            "detail_id = #{detailId}, " +
            "description = #{description}, " +
            "contact_phone = #{contactPhone}, " +
            "user_address = #{userAddress}, " +
            " area = #{area}, " +
            "order_status = #{orderStatus}, " +
            "priority = #{priority}, " +
            "price = #{price}, " +
            "finish_pic = #{finishPic} " +
            "WHERE id = #{id}")
    void updateOrder(Order order);

    @Update("UPDATE orders SET assigned_engineer = #{assignedEngineer} WHERE id = #{id}")
    void assignedEngineer(Order order);

    @Select("SELECT * FROM orders WHERE assigned_engineer = #{assignedEngineer}")
    Order getOrderByEngineer(int assignedEngineer);

    @Select("SELECT * FROM orders WHERE area = #{area}")
    Order viewOrder(String area);

    @Delete("DELETE FROM orders WHERE id = #{id}")
    void deleteOrder(Long id);


    Page<Order> pageQuery(
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize,
            @Param("id") Long id,
            @Param("contactPhone") String contactPhone,
            @Param("beginTime") LocalDate beginTime,
            @Param("endTime") LocalDate endTime,
            String flag,
            String region
    );

    @Select("SELECT * FROM orders")
    List<Order> getAll();

    void receiveOrder(String orderId, Long engId);
    void finishOrder(String id);
    @Select("SELECT DATE(created_time) AS order_date, COUNT(*) AS order_count " +
            "FROM orders " +
            "WHERE created_time BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY DATE(created_time) " +
            "ORDER BY DATE(created_time)")
    List<OrderStatistic> getOrderStatistics(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    void cancel(String id);

    @Select("SELECT * FROM orders WHERE assigned_engineer = #{empId}")
    List<Order> getOrdersByEmpId(Long empId);

    Integer getTime(String time);

    List<Order> getByRegion(String flag, String region);
}
