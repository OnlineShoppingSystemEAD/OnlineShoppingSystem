package com.example.product_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.product_management.service.ItemService;
import com.example.product_management.model.Item;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Get limited items
    @GetMapping
    public ResponseEntity<List<Item>> getLimitesItems(@RequestParam(defaultValue = "16") int limit) {
        List<Item> items = itemService.getLimitedItems(limit);
        return items.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(items);
    }

    // Get specific items
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItembyId(@PathVariable String id) {
        return itemService.getItembyId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Get items by category
    @GetMapping("/{categoryId}")
    public List<Item> getItemsByCategoryId(@PathVariable String categoryId) {
        return itemService.getItemsByCategory(categoryId);
    }
}
