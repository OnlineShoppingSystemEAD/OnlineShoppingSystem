package com.example.product_management.controller;

import com.example.product_management.dto.ItemDTO;
import com.example.product_management.dto.CategoryDTO;
import com.example.product_management.model.Item;
import com.example.product_management.service.CategoryService;
import com.example.product_management.service.ItemService;
import com.example.usermanagement.Service.OurUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminItemController {

    @Autowired
    private ItemService itemService;

    // To verify user roles
    @Autowired
    private OurUserDetailsService ourUserDetailsService;

    // Add a new item to a category
    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestHeader("userId") Integer userId,
                                        @RequestHeader("role") String role,
                                        @RequestBody ItemDTO itemDTO) {
        Item createdItem = itemService.addItem(itemDTO);
        return ResponseEntity.ok(createdItem);
    }
    
    // Update an item in a category
    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@RequestHeader("userId") Integer userId,
                                           @RequestHeader("role") String role,
                                           @PathVariable int id,
                                           @RequestBody ItemDTO updatedItemDTO) {
        Item item = itemService.updateItem(id, updatedItemDTO);
        return ResponseEntity.ok(item);
    }

    // Delete an item by ID
    @DeleteMapping("/items/{id}")
    public ResponseEntity<String> deleteItem(@RequestHeader("userId") Integer userId,
                                             @RequestHeader("role") String role,
                                             @PathVariable int id) {
        itemService.deleteItem(id);
        return ResponseEntity.ok("Item deleted successfully.");
    }

    // admin category control part

    @Autowired
    private CategoryService categoryService;

    // Fetch category by ID (admin can also view them)
    @GetMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    // Create a new category
    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestHeader("userId") Integer userId,
                                                      @RequestHeader("role") String role,
                                                      @RequestBody CategoryDTO categoryDTO) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Return 403 Forbidden if not admin
        }

        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Update an existing category
    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@RequestHeader("userId") Integer userId,
                                                      @RequestHeader("role") String role,
                                                      @PathVariable int id,
                                                      @RequestBody CategoryDTO categoryDTO) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Return 403 Forbidden if not admin
        }

        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    // Delete a category
    @DeleteMapping("categories/{id}")
    public ResponseEntity<Void> deleteCategory(@RequestHeader("userId") Integer userId,
                                               @RequestHeader("role") String role,
                                               @PathVariable int id) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Return 403 Forbidden if not admin
        }

        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
