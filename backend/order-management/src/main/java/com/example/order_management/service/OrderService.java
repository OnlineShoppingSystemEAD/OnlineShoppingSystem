package com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.example.order_management.dto.ShoppingCartItemDto;
import com.example.order_management.model.Orders;
import com.example.order_management.repository.OrderRespository;
import com.example.order_management.model.OrderItems;

@Service
public class OrderService {
    @Autowired
    private OrderRespository orderRespository;

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private OrderItemsService orderItemService;

    public Orders createOrder(int userId, Orders order) {
        Orders newOrder = orderRespository.save(order);

        List<ShoppingCartItemDto> cartItems = shoppingCartService.getShoppingCartItemsForOrderItems(userId);

        for (ShoppingCartItemDto cartItem : cartItems) {
            OrderItems orderItem = new OrderItems();
            orderItem.setOrder(newOrder);
            orderItem.setItemId(cartItem.getId());
            orderItem.setQuantity(cartItem.getItemQuantity());
            orderItemService.createOrderItem(orderItem);

            deleteItemFromtheShoppingCart(cartItem.getId());

        }
        // Send a request to the payment-management
        return newOrder;
    }

    // Update Payment Id and Status after payment
    public Orders updateOrderStatus(int orderId, Orders order) {
        Orders orderDetails = orderRespository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        orderDetails.setPaymentId(order.getPaymentById());
        orderDetails.setStatus(Orders.Status.PAID);
        return orderRespository.save(orderDetails);
    }

    // Delete item from the shopping cart after creating the orderItem
    public void deleteItemFromtheShoppingCart(int id) {
        shoppingCartService.deleteItemFromtheShoppingCart(id);
    }

}
