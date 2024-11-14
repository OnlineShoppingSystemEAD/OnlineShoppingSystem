package com.example.order_management.dto;

public class ShoppingCartItemDto {
    private int id;
    private String itemName;
    private float itemPrice;
    private int itemQuantity;

    public ShoppingCartItemDto() {
    }

    public ShoppingCartItemDto(int id, String itemName, float itemPrice, int itemQuantity) {
        this.id = id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemQuantity = itemQuantity;
    }

    public ShoppingCartItemDto(int id, int itemQuantity) {
        this.id = id;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return this.itemName;
    }

    public float getItemPrice() {
        return this.itemPrice;
    }

    public int getItemQuantity() {
        return this.itemQuantity;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
