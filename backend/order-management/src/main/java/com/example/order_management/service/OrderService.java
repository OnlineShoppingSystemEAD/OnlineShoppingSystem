package com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.order_management.model.Orders;
import com.example.order_management.repository.OrderRespository;

@Service
public class OrderService {
    @Autowired
    private OrderRespository orderRespository;

    public Orders createOrder(Orders order) {
        return orderRespository.save(order);
    }
}
