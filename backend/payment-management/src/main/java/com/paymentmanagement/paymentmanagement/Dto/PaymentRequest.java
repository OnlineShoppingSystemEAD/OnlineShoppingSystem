package com.paymentmanagement.paymentmanagement.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private int userID;
    private int orderId;
    private double amount;

    public PaymentRequest(int orderId, double amount, int userID) {
        this.orderId = orderId;
        this.amount = amount;
        this.orderId = orderId;
    }

    public PaymentRequest(int i, BigDecimal bigDecimal, String creditCard) {
    }

    public PaymentRequest(int i, double v, String creditCard) {
    }
}
