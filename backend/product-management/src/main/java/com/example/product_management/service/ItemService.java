package com.example.product_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.product_management.repository.ItemRepository;
import com.example.product_management.model.Item;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getLimitedItems(int limit) {
        return itemRepository.findLimitedItems(limit);
    }

    public Optional<Item> getItembyId(String id) {
        return itemRepository.findById(id);
    }

    // Display Items by category
    public List<Item> getItemsByCategory(String categoryId) {
        return itemRepository.findByCategoryId(categoryId);
    }
}
