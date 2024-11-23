package com.example.order_management.dto;

@Data
public class PaymentRequest {
    private int orderId;
    private double amount;

    public PaymentRequest(int orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

}
