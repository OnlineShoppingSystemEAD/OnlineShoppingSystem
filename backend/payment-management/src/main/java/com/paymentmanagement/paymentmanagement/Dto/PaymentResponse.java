package com.paymentmanagement.paymentmanagement.Dto;

public class PaymentResponse {
    private String orderId;
    private Long paymentId;
    private double totalAmount;

    public PaymentResponse(String orderId, Long paymentId, double totalAmount) {
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.totalAmount = totalAmount;
    }
}
