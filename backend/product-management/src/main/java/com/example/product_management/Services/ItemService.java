package com.example.product_management.Services;

import com.example.product_management.Entities.Item;
import com.example.product_management.Repositories.ItemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Retrieve all items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Retrieve item by ID
    public Optional<Item> getItemById(String id) {
        return itemRepository.findById(id);
    }

    // Create and save a new item
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    // Update an existing item
    public Item updateItem(String id, Item itemDetails) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());
        item.setQuantity(itemDetails.getQuantity());
        item.setImage(itemDetails.getImage());
        item.setCategoryId(itemDetails.getCategoryId());

        return itemRepository.save(item);
    }

    // Delete an item by ID
    public void deleteItem(String id) {
        if (!itemRepository.existsById(id)) {
            throw new IllegalArgumentException("Item not found");
        }
        itemRepository.deleteById(id);
    }

    // Add an existing item to a specific category
    public Item addItemToCategory(String itemId, String categoryId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));

        item.setCategoryId(categoryId);
        return itemRepository.save(item);
    }
}
