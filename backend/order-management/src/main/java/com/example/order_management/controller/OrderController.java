package com.example.order_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.order_management.model.Orders;
import com.example.order_management.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/")
    public List<Orders> getAllOrders(){
        return orderService.getAllOrders();
    }

    @PostMapping("/createOrder/{userId}")
    public Orders createOrder(@RequestBody Orders order, @RequestParam int userId) {
      return orderService.createOrder(userId, order);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<Orders> updateOrderStatus(@PathVariable @RequestParam int orderId,
            @RequestBody Orders order) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, order));
    }

    @DeleteMapping("/deleteOrder")
    public String deleteOrder(@PathVariable @RequestParam int orderId){
        String success = orderService.deleteOrder(orderId);
        return success;

    }
}
