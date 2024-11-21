package com.paymentmanagement.paymentmanagement.Dto;


import lombok.Data;

@Data
public class ReqPayment {
    private int paymentId;
    private int orderId;
    private int amount;
    private date paymentDate;
    private String status;

    private class date {
    }
}
