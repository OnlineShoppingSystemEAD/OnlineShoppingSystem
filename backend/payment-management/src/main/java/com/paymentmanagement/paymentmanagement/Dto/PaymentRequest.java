package com.paymentmanagement.paymentmanagement.Dto;

import lombok.Data;

@Data
public class PaymentRequest {
    private int orderId;
    private double amount;

}
