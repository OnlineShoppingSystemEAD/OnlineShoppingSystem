package com.example.order_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.order_management.service.ShoppingCartService;
import com.example.order_management.model.ShoppingCartItem;
import com.example.order_management.dto.ShoppingCartItemDto;
import java.util.List;


@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    // Get Shopping Cart Items by User Id for viewing
    @GetMapping("/{userId}")
    public ResponseEntity<List<ShoppingCartItemDto>> getShoppingCartByUserId(@PathVariable int userId) {
        return shoppingCartService.getShoppingCartByUserId(userId)
                .filter(list -> !list.isEmpty()) // Ensures the list is not empty
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update the Shopping Cart Items
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCartItem> updateShoppingCart(@PathVariable int id,
            @RequestBody ShoppingCartItem shoppingCartItemDetails) {
        return ResponseEntity.ok(shoppingCartService.updateShoppingCart(id,
                shoppingCartItemDetails));
    }

    // Delete Item from the Shopping Cart
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItemFromtheShoppingCart(@PathVariable int id, @RequestParam int userId) {
        shoppingCartService.deleteItemFromtheShoppingCart(id,userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/addItem")
    public ShoppingCartItem createShoppingCartItem(@RequestBody ShoppingCartItem shoppingCartItem) {
        return shoppingCartService.createShoppingCartItem(shoppingCartItem);
    }
}
