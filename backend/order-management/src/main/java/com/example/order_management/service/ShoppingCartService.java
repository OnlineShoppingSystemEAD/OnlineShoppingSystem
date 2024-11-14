package com.example.order_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.order_management.repository.ShoppingCartRepository;
import java.util.List;
import java.util.stream.Collectors;
import com.example.order_management.dto.ItemDetailsDto;
import com.example.order_management.dto.ShoppingCartItemDto;
import com.example.order_management.model.ShoppingCartItem;
import java.util.Optional;

@Service
public class ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    // Get Shopping Cart Items for viewing
    public Optional<List<ShoppingCartItemDto>> getShoppingCartByUserId(int userId) {
        List<ShoppingCartItem> items = shoppingCartRepository.findByUserId(userId);

        List<ShoppingCartItemDto> itemDTOs = items.stream().map(item -> {
            ItemDetailsDto itemDetails = getItem(item.getItemId());
            ShoppingCartItemDto dto = new ShoppingCartItemDto();
            dto.setId(itemDetails.getId());
            dto.setItemQuantity(itemDetails.getQuantity());
            dto.setItemName(itemDetails.getName());
            dto.setItemPrice(itemDetails.getPrice());
            return dto;
        }).collect(Collectors.toList());

        return itemDTOs.isEmpty() ? Optional.empty() : Optional.of(itemDTOs);
    }

    // Get Shopping Cart Items to create order items
    public List<ShoppingCartItemDto> getShoppingCartItemsForOrderItems(int userId) {
        List<ShoppingCartItem> items = shoppingCartRepository.findByUserId(userId);
        return items.stream()
                .map(item -> new ShoppingCartItemDto(item.getItemId(), item.getQuantity()))
                .collect(Collectors.toList());
    }

    // Updating shopping cart quantity
    public ShoppingCartItem updateShoppingCart(int id, ShoppingCartItem shoppingCartItemDetails) {
        ShoppingCartItem shoppingCartItem = shoppingCartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shopping Cart Item Not Found"));
        // Logic for updating the quantity
        shoppingCartItem.setQuantity(shoppingCartItemDetails.getQuantity());
        return shoppingCartRepository.save(shoppingCartItem);
    }

    // Remove item from the shopping cart
    public void deleteItemFromtheShoppingCart(int id) {
        shoppingCartRepository.deleteById(id);
    }

    // Add a new item to the shopping cart
    public ShoppingCartItem createShoppingCartItem(ShoppingCartItem shoppingCartItem) {
        return shoppingCartRepository.save(shoppingCartItem);
    }

    // Get Item Details
    public ItemDetailsDto getItem(int itemId) {
        RestTemplate restTemplate = new RestTemplate();
        ItemDetailsDto responseItemDetails = restTemplate.getForObject(
                "http://localhost:8080/api/items/{itemId}", ItemDetailsDto.class, itemId);
        return responseItemDetails;
    }
}
