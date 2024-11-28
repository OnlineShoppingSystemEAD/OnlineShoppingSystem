package com.example.product_management.controller;

import com.example.product_management.model.Item;
import com.example.product_management.service.AdminService;
import com.example.usermanagement.Service.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminItemController {

    @Autowired
    private AdminService adminService;

    // To verify user roles
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    // Add a new item to a category
    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestHeader("userId") Integer userId,
                                        @RequestHeader("role") String role,
                                        @RequestBody Item item) {
        Item createdItem = adminService.addItem(item);
        return ResponseEntity.ok(createdItem);
    }
    
    // Update an item in a category
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@RequestHeader("userId") Integer userId,
                                           @RequestHeader("role") String role,
                                           @PathVariable int id,
                                           @RequestBody Item updatedItem) {
        Item item = adminService.updateItem(id, updatedItem);
        return ResponseEntity.ok(item);
    }

    // Delete an item by ID
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@RequestHeader("userId") Integer userId,
                                             @RequestHeader("role") String role,
                                             @PathVariable int id) {
        adminService.deleteItem(id);
        return ResponseEntity.ok("Item deleted successfully.");
    }
}
