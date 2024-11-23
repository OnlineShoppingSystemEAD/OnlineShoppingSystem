package com.example.order_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.order_management.model.Orders;
import com.example.order_management.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public Orders createOrder(@RequestBody Orders order, @RequestParam int userId) {
        return orderService.createOrder(userId, order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable @RequestParam int orderId,
            @RequestBody Orders order) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, order));
    }
}
