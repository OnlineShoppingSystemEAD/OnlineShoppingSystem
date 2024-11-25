package com.example.order_management.dto;

@Data
public class PaymentRequest {
    private int orderId;
    private double amount;

    public PaymentRequest(int orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public PaymentRequest() {
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getOrderId() {
        return this.orderId;
    }

    public double getaAmount() {
        return this.amount;
    }

}
