package com.example.product_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.product_management.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItembyId(String id) {
        return itemRepository.findById(id);
    }

    // Display Items by category
    public List<Item> getItemsByCategory(String name) {

    }
}
