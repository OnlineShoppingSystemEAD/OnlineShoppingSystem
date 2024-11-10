package com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.order_management.repository.ShoppingCartRepository;
import java.util.List;
import com.example.order_management.model.ShoppingCartItem;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public Optional<List<ShoppingCartItem>> getShoppingCartByUserId(int userId) {
        List<ShoppingCartItem> items = shoppingCartRepository.findByUserId(userId);
        return items.isEmpty() ? Optional.empty() : Optional.of(items);
    }

    public ShoppingCartItem updateShoppingCart(int id, ShoppingCartItem shoppingCartItemDetails) {
        ShoppingCartItem shoppingCartItem = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping Cart Item Not Found"));
        // Logic for updating the quantity
        shoppingCartItem.setQuantity(shoppingCartItemDetails.getQuantity());
        return shoppingCartRepository.save(shoppingCartItem);
    }

    public void deleteItemFromtheShoppingCart(int id) {
        shoppingCartRepository.deleteById(id);
    }

    public ShoppingCartItem createShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        return shoppingCartRepository.save(shoppingCartItem);
    }
}
