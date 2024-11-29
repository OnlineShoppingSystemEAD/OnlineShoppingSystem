package com.example.product_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.data.domain.Page;
import com.example.product_management.service.ItemService;
import com.example.product_management.model.Item;

import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Get specific items
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItembyId(@PathVariable int id) {
        return itemService.getItembyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Get items by category
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Page<Item>> getItemsByCategory(@PathVariable int categoryId,
            @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "16") int pageSize) {
        Page<Item> items = itemService.getItemsByCategory(categoryId, pageNo, pageSize);
        return ResponseEntity.ok(items);
    }

    // Get Items
    @GetMapping
    public ResponseEntity<Page<Item>> getItems(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "16") int pageSize) {
        Page<Item> items = itemService.getItems(pageNo, pageSize);
        return ResponseEntity.ok(items);
    }

    // ITEM MANAGEMENT BY ADMIN

    // Add a new item to a category
    @PostMapping("/add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        // Assuming the 'categoryId' is set in the Item object
        Item savedItem = itemService.addItemToCategory(item);
        return ResponseEntity.ok(savedItem);
    }

    // Update an existing item in a category
    @PutMapping("/update/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item item) {
        Optional<Item> updatedItem = itemService.updateItem(id, item);
        return updatedItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete an item from a category
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        boolean isDeleted = itemService.deleteItem(id);
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();

    }
}
