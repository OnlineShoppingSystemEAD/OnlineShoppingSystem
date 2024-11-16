package com.example.product_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.product_management.repository.ItemRepository;
import com.example.product_management.model.Item;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Page<Item> getItems(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> getItembyId(int id) {
        return itemRepository.findById(id);
    }

    // Display Items by category
    public Page<Item> getItemsByCategory(String categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }

    // ITEM MANAGEMENT BY ADMIN
    
    // Add a new item to a category
    public Item addItemToCategory(Item item) {
        // You can add custom logic here to associate the item with a category
        return itemRepository.save(item);
    }

    // Update an existing item
    public Optional<Item> updateItem(int id, Item item) {
        if (itemRepository.existsById(id)) {
            item.setId(id);  // Ensure the item's ID stays the same
            return Optional.of(itemRepository.save(item));
        }
        return Optional.empty();
    }

    // Delete an item from a category
    public boolean deleteItem(int id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
