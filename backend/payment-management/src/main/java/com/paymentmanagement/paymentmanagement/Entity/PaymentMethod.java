package com.paymentmanagement.paymentmanagement.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "payment_details")
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private String cardNumber; // encrypt this in production

    @Column(nullable = false)
    private String expirationDate;

    @Column(nullable = false)
    private String cvv; //  hash this in production

    @Column(nullable = false)
    private String nickname;
}
