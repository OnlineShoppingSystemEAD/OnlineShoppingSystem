package com.paymentmanagement.paymentmanagement.Dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private String orderId;
    private double totalAmount;
}
