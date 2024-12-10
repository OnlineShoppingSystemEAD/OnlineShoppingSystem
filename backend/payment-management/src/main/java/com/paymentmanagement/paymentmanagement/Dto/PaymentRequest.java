package com.paymentmanagement.paymentmanagement.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private int userID;
    private int orderId;
    private double amount;


    public PaymentRequest(int i, BigDecimal bigDecimal, String creditCard) {
    }

    public PaymentRequest(int i, double v, String creditCard) {
    }
}
