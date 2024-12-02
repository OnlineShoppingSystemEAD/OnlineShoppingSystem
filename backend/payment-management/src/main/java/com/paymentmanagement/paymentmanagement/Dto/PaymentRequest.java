package com.paymentmanagement.paymentmanagement.Dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private int orderId;
    private double amount;

    public void setAmount(BigDecimal bigDecimal) {
    }


    public void setCurrency(String usd) {
    }
}
