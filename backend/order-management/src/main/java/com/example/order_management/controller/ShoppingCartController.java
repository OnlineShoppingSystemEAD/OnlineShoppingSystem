package main.java.com.example.order_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.order.management.service.ShoppingCartService;
import com.example.order.management.model.ShoppingCartItem;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    // Get Shopping Cart Items by User Id
    @GetMapping("/{userId}")
    public ResponseEntity<List<ShoppingCartItem>> getShoppingCartByUserId(@PathVariable int userId) {
        return shoppingCartService.getShoppingCartByUserId(userId).map(ResponseEntity::ok)
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
    public ResponseEntity<Void> deleteItemFromtheShoppingCart(@PathVariable int id) {
        shoppingCartService.deleteItemFromtheShoppingCart(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ShoppingCartItem createShoppingCartItem(@RequestBody ShoppingCartItem shoppingCartItem) {
        return shoppingCartService.createShoppingCartItem(shoppingCartItem);
    }
}
