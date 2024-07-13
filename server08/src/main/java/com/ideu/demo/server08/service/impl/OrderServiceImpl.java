package com.ideu.demo.server08.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ideu.demo.server08.common.PageResult;
import com.ideu.demo.server08.common.RedisConstant;
import com.ideu.demo.server08.entity.*;
import com.ideu.demo.server08.mapper.EngineerMapper;
import com.ideu.demo.server08.mapper.OrderMapper;
import com.ideu.demo.server08.mapper.PaymentMapper;
import com.ideu.demo.server08.mapper.UserMapper;
import com.ideu.demo.server08.service.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // 添加事务注解
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    EngineerMapper engineerMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    PaymentMapper paymentMapper;
    @Override
    public void addOrder(Order order) {
        orderMapper.addOrder(order);
    }

    @Override
    public Order getOrderById(String id) {
        return orderMapper.getOrderById(id);
    }

    @Override
    public List<Order> getAll() {

        return orderMapper.getAll();
    }

    @Override
    public List<UserOrderVo> getOrdersByUserId(Long userId) {
        List<UserOrderVo>vos=new ArrayList<>();

        List<Order>orders=orderMapper.getOrdersByUserId(userId);
        orders.stream().forEach(order -> {
            UserOrderVo v=new UserOrderVo();
            BeanUtils.copyProperties(order,v);
            if(order.getAssignedEngineer()!=null){
                if(userMapper.getById(order.getAssignedEngineer())!=null){
                    v.setEngName(userMapper.getById(order.getAssignedEngineer()).getName());
                    //TODO 接单时、要选择称谓
                    v.setEngPhone(userMapper.getById(order.getAssignedEngineer()).getPhone());
                }

            }

            vos.add(v);
        });
        return vos;
    }

    @Override
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
    }

    @Override
    public void deleteOrder(Long orderId) {
        orderMapper.deleteOrder(orderId);
    }
    @Override
    public void assignedEngineer(Order order){
        orderMapper.assignedEngineer(order);
    }

    public Order viewOrder(String area){
        return orderMapper.viewOrder(area);
    }

    public Order getOrderByEngineer(int assignedEngineer){
        return orderMapper.getOrderByEngineer(assignedEngineer);
    }
    @Override
    public PageResult pageQuery(Integer pageNum, Integer pageSize, Long id, String contactPhone, LocalDate beginTime, LocalDate endTime,String flag,String region) {
        PageHelper.startPage(pageNum, pageSize);
        if(endTime!=null)
        endTime=endTime.plusDays(1l);
        pageNum--;
        String[] split = region.split("-");
        region=split[0];
        Page<Order> page = orderMapper.pageQuery(pageNum,pageSize,id,contactPhone,beginTime,endTime,flag,region);
        List<Order>page2=orderMapper.getByRegion(flag,region);
        return new PageResult(page.getResult(), page.getTotal(), pageNum, pageSize);
//        return new PageResult(page2, page2.size(), pageNum, pageSize);
    }

    @Override
    public List<String> getTypes() {
        return null;
    }

    @Override
    public String receiveOrder(String orderId, Long engId) {
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent(RedisConstant.ORDER_KEY + orderId, engId.toString());
        if(isLock){
            if("2".equals(orderMapper.getOrderById(orderId).getOrderStatus())){
                return "该订单已完成!";
            }
            orderMapper.receiveOrder(orderId,engId);
            return "您已成功接单";
        }
        return "0";
    }

    @Override
    public void finishOrder(Order order,Long userId) {
        userMapper.updateBalance(order.getBalance(),userId);
        orderMapper.finishOrder(order.getId());
    }
    @Override
    public List<OrderStatistic> getOrderStatistics(Date startDate, Date endDate) {
        return orderMapper.getOrderStatistics(startDate, endDate);
    }

    @Override
    public void cancel(String id) {
        orderMapper.cancel(id);
    }

    @Override
    public List<EmpOrderVo> getEmpOrders(String userId) {
        List<Order>orders=orderMapper.getOrdersByEmpId(Long.valueOf(userId));
        List<EmpOrderVo> vos = orders.stream().map(e -> {
            EmpOrderVo v = new EmpOrderVo();
            BeanUtils.copyProperties(e, v);
            Payment payment = paymentMapper.getByOrderId(e.getId());
            v.setAmount(payment.getAmount());
            return v;
        }).collect(Collectors.toList());
        return vos;
    }

    @Override
    public OrderStatisticsVo getOrderStatistics2(LocalDate startDate, LocalDate endDate) {
        OrderStatisticsVo vo=new OrderStatisticsVo();
        StringBuilder count=new StringBuilder();
       StringBuilder  time=new StringBuilder();
        for(LocalDate date=startDate;date.isBefore(endDate);date=date.plusDays(1l)){
               Integer i= orderMapper.getTime(date.toString());
               if(i==null) i=0;
               if(StringUtils.isEmpty(count)){
                   count.append(i);
               }else {
                   count.append(",").append(i);
               }
               if(StringUtils.isEmpty(time)){
                   time.append(date.toString());
               }else {
                   time.append(",").append(date.toString());
               }
        }
        vo.setCount(count.toString());
        vo.setDate(time.toString());
        return vo;
    }
}
