package com.ideu.demo.server08.service.impl;

import com.ideu.demo.server08.entity.AfterSales;
import com.ideu.demo.server08.mapper.AfterSalesMapper;
import com.ideu.demo.server08.service.AfterSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AfterSalesServiceImpl implements AfterSalesService {

    private final AfterSalesMapper afterSalesMapper;

    @Autowired
    public AfterSalesServiceImpl(AfterSalesMapper afterSalesMapper) {
        this.afterSalesMapper = afterSalesMapper;
    }

    @Override
    public void addOrderService(AfterSales afterSales) {
        afterSales.setCreatedTime(LocalDateTime.now());
        afterSalesMapper.insertOrderService(afterSales);
    }

    @Override
    public AfterSales getOrderServiceById(Long id) {
        return afterSalesMapper.getOrderServiceById(id);
    }

    @Override
    public List<AfterSales> getOrderServicesByOrderId(Long orderId) {
        return afterSalesMapper.getOrderServicesByOrderId(orderId);
    }

    @Override
    public void updateOrderService(AfterSales afterSales) {
        afterSalesMapper.updateOrderService(afterSales);
    }

    @Override
    public void deleteOrderServiceById(Long id) {
        afterSalesMapper.deleteOrderServiceById(id);
    }

    @Override
    public List<AfterSales> getAllOrderServices() {
        return afterSalesMapper.getAllOrderServices();
    }
}
