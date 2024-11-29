package com.example.product_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.product_management.repository.ItemRepository;
import com.example.product_management.model.Item;
import com.example.product_management.exception.ItemNotFoundException;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public Page<Item> getItems(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> getItemById(int id) {
        if (itemRepository.existsById(id)) {
            return itemRepository.findById(id);
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    // Display Items by category
    public Page<Item> getItemsByCategory(int categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }

    // ITEM MANAGEMENT BY ADMINd

    // Add a new item to a category
    public Item addItemToCategory(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            throw new RuntimeException("Error adding item to category");
        }
    }

    // Update an existing item
    public Optional<Item> updateItem(int id, Item item) {
        if (itemRepository.existsById(id)) {
            return Optional.of(itemRepository.save(item));
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }

    // Delete an item from a category
    public boolean deleteItem(int id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
            return true;
        } else {
            throw new ItemNotFoundException("Item not found with id: " + id);
        }
    }
}
