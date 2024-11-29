package com.example.product_management.Controller;

import com.example.product_management.model.Item;
import com.example.product_management.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Add a new item to a category
    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item createdItem = adminService.addItem(item);
        return ResponseEntity.ok(createdItem);
    }

    // Update an item in a category
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item updatedItem) {
        Item item = adminService.updateItem(id, updatedItem);
        return ResponseEntity.ok(item);
    }

    // Delete an item by ID
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable int id) {
        adminService.deleteItem(id);
        return ResponseEntity.ok("Item deleted successfully.");
    }
}
