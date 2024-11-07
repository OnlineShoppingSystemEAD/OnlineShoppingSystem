package main.java.com.example.order_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.order.management.service.ShoppingCartService;
import com.example.order.management.model.ShoppingCart;

@RestController
@RequestMapping("/api/shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    // Get Shopping Cart by User Id
    @GetMapping("/{userId}")
    public ResponseEntity<ShoppingCart> getShoppingCartByUserId(@PathVariable String userId) {
        return shoppingCartService.getShoppingCartByUserId(userId).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Update the Shopping Cart
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingCart> updateShoppingCart(@PathVariable String id,
            @RequestBody ShoppingCart shoppingCartDetails) {
        return ResponseEntity.ok(shoppingCartService.updateShoppingCart(id, shoppingCartDetails));
    }

    // Delete Item from the Shopping Cart
}
