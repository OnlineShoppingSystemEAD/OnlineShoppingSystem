package com.example.product_management.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String description;
    private float price;
    private int quantity;
    private Object image;
    private String categoryId;

    public Item() {
    }

    public Item(String name, String description, float price, int quantity, Object image, String categoryId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.image = image;
        this.categoryId = categoryId;
    }

}
