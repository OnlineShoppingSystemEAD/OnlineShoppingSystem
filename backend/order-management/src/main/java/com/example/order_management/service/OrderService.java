package com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.example.order_management.dto.PaymentRequest;
import com.example.order_management.dto.PaymentResponse;
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

    @Autowired
    private RestTemplate restTemplate;

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
        PaymentRequest paymentRequest = new PaymentRequest(order.getId(), order.getTotalAmount());
        PaymentResponse paymentResponse = sendPaymentRequest(paymentRequest);

        if (paymentResponse != null && paymentResponse.getPaymentId() != 0) {
            // Update order status to PAID
            order.setPaymentId(paymentResponse.getPaymentId());
            updateOrderStatus(order.getId(), order);
        }

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

    public PaymentResponse sendPaymentRequest(PaymentRequest paymentRequest) {
        String url = "http://your-api-url/payment"; // Replace with actual URL

        // Send POST request
        ResponseEntity<PaymentResponse> responseEntity = restTemplate.postForEntity(url, paymentRequest,
                PaymentResponse.class);

        // Return the response body
        return responseEntity.getBody();
    }

}
