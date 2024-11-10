package com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.order_management.model.OrderItems;
import com.example.order_management.repository.OrderItemsRepository;

@Service
public class OrderItemsService {
    @Autowired
    private OrderItemsRepository orderItemRepository;

    public OrderItems createOrderItem(OrderItems orderItems) {
        return orderItemRepository.save(orderItems);
    }
}
