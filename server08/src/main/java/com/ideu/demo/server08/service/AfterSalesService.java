package com.ideu.demo.server08.service;

import com.ideu.demo.server08.entity.AfterSales;

import java.util.List;

public interface AfterSalesService {

    void addOrderService(AfterSales afterSales);

    AfterSales getOrderServiceById(Long id);

    List<AfterSales> getOrderServicesByOrderId(Long orderId);

    void updateOrderService(AfterSales afterSales);

    void deleteOrderServiceById(Long id);

    List<AfterSales> getAllOrderServices();
}
