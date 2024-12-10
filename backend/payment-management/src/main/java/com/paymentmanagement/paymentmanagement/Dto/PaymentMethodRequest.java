package com.paymentmanagement.paymentmanagement.Dto;
import lombok.Data;

@Data
public class PaymentMethodRequest {
    private int userId;
    private String cardHolderName;
    private String cardNumber;
    private String expirationDate;
    private String cvv;
    private String nickname;

    public PaymentMethodRequest(String creditCard) {
    }

    public PaymentMethodRequest(int i, String johnDoe, String number, String s, int i1, String personalCard) {
    }
}
