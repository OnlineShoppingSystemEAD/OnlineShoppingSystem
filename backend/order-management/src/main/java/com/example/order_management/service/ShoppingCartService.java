package main.java.com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.order.management.model.ShoppingCart;
import com.example.order.management.repository.ShoppingCartRepository;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    public Optional<ShoppingCart> getShoppingCartByUserId(String userId) {
        return shoppingCartRepository.findById(userId);
    }

    public ShoppingCart updateShoppingCart(String id, ShoppingCart shoppingCartDetails) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping Cart Not Found"));
        // Logic for the update
        return shoppingCartRepository.save(shoppingCart);
    }
}
