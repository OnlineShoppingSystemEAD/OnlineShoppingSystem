package com.example.product_management.service;

import com.example.product_management.model.Item;
import com.example.product_management.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private ItemRepository itemRepository;

    // Add a new item to a category
    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    // Update an existing item
    public Item updateItem(int id, Item updatedItem) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        if (optionalItem.isPresent()) {
            Item existingItem = optionalItem.get();
            existingItem.setName(updatedItem.getName());
            existingItem.setDescription(updatedItem.getDescription());
            existingItem.setPrice(updatedItem.getPrice());
            existingItem.setQuantity(updatedItem.getQuantity());
            existingItem.setCategoryId(updatedItem.getCategoryId());
            return itemRepository.save(existingItem);
        } else {
            throw new RuntimeException("Item not found with id: " + id);
        }
    }

    // Delete an item by ID
    public void deleteItem(int id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new RuntimeException("Item not found with id: " + id);
        }
    }
}
