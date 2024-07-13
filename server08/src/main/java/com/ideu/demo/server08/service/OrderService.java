package com.ideu.demo.server08.service;

import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.entity.*;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public interface OrderService
{

    void addOrder(Order order);

    Order getOrderById(String orderId);

    List<Order> getAll();

    List<UserOrderVo> getOrdersByUserId(Long userId);

    void updateOrder(Order order);

    void deleteOrder(Long orderId);

    void assignedEngineer(Order order);

    Order viewOrder(String area);

    Order getOrderByEngineer(int assignedEngineer);

    PageResult pageQuery(Integer pageNum, Integer pageSize, Long id, String contactPhone, LocalDate beginTime, LocalDate endTime,String flag,String region);

    List<String> getTypes();

    String receiveOrder(String orderId, Long engId);

    void finishOrder(Order order,Long userId);

    List<OrderStatistic> getOrderStatistics(Date startDate, Date endDate);

    void cancel(String id);

    List<EmpOrderVo> getEmpOrders(String userId);

    OrderStatisticsVo getOrderStatistics2(LocalDate startDate, LocalDate endDate);
}
