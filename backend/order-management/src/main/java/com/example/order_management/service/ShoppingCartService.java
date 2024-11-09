package main.java.com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.order.management.model.ShoppingCart;
import com.example.order.management.repository.ShoppingCartRepository;

import main.java.com.example.order_management.model.ShoppingCartItem;

import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public List<ShoppingCartItem> getShoppingCartByUserId(String userId) {
        return shoppingCartRepository.findByUserId(userId);
    }

    public ShoppingCartItem updateShoppingCart(int id, ShoppingCartItem shoppingCartItemDetails) {
        ShoppingCartItem shoppingCartItem = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping Cart Item Not Found"));
        shoppingCartItem.setQuantity(shoppingCartItemDetails.setQuantity());
        return shoppingCartRepository.save(shoppingCartItem);
    }

    public void deleteItemFromtheShoppingCart(int id) {
        shoppingCartRepository.deleteByItemId(id);
    }

    public ShoppingCartItem createShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        return shoppingCartRepository.save(shoppingCartItem);
    }
}
