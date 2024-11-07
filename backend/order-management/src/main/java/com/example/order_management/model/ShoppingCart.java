package main.java.com.example.order_management.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String userId;

    public ShoppingCart() {
    }

    public ShoppingCart(String userId) {
        this.userId = userId;
    }
}
