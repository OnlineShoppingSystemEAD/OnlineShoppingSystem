package com.example.product_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final ItemService itemService;
    private final CategoryService categoryService;

    @Autowired
    public AdminService(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    public String addItemToCategory(Long categoryId, Item item) {
        Category category = categoryService.addCategory(new Category(categoryId, item.getName())); // Just for example, add a category if not found
        item.setCategory(category);
        itemService.addItem(item);
        return "Item added to category!";
    }

    public String updateItem(Long id, Item item) {
        Item updatedItem = itemService.updateItem(id, item);
        if (updatedItem != null) {
            return "Item updated!";
        }
        return "Item not found!";
    }

    public String deleteItem(Long id) {
        if (itemService.deleteItem(id)) {
            return "Item deleted!";
        }
        return "Item not found!";
    }
}
