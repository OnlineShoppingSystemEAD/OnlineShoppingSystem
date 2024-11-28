package com.example.product_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Page<Item>> getItemsByCategory(@PathVariable String categoryId,
                                                         @RequestParam(defaultValue = "0") int pageNo,
                                                         @RequestParam(defaultValue = "16") int pageSize) {
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

}
