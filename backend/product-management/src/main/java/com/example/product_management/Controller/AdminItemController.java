package com.example.product_management.controller;

import com.example.product_management.dto.CategoryDTO;
import com.example.product_management.dto.ItemDTO;
import com.example.product_management.service.AdminService;
import com.example.product_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminItemController {

    @Autowired
    private AdminService adminService;

    // Get all items
    @GetMapping("/item")
    public ResponseEntity<List<ItemDTO>> getAllItems() {
        return ResponseEntity.ok(adminService.getAllItems());
    }

    // Create a new item
    @PostMapping("/item")
    public ResponseEntity<ItemDTO> addItem(
            @RequestParam("CategoryId") int categoryId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("stock") int stock,
            @RequestParam("Supplier") String supplier,
            @RequestParam("price") float price,
            @RequestParam("image") MultipartFile image) {

        ItemDTO itemDTO = new ItemDTO(categoryId, name, description, stock, supplier, price, image);
        ItemDTO createdItem = adminService.addItem(itemDTO);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    // Update an existing item
    @PutMapping(value = "/{itemid}", consumes = "multipart/form-data")
    public ResponseEntity<ItemDTO> updateItem(
            @PathVariable("itemid") int itemId,
            @RequestParam("CategoryId") int categoryId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("stock") int stock,
            @RequestParam("Supplier") String supplier,
            @RequestParam("price") float price,
            @RequestParam("image") MultipartFile image) {

        ItemDTO itemDTO = new ItemDTO(categoryId, name, description, stock, supplier, price, image);
        ItemDTO updatedItem = adminService.updateItem(itemId, itemDTO);
        return updatedItem != null ? ResponseEntity.ok(updatedItem) : ResponseEntity.notFound().build();
    }

    // Delete an item
    @DeleteMapping("/{Itemid}")
    public ResponseEntity<Object> deleteItem(@PathVariable("Itemid") int itemId) {
        adminService.deleteItem(itemId);
        return ResponseEntity.ok().body("{\"message\": \"Item deleted successfully\"}");
    }

    // Category Management by ADMIN

    @Autowired
    private CategoryService categoryService;

    //get all category by admin
    @GetMapping("/category")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    // Get category by ID
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("categoryId") int categoryId) {
        CategoryDTO category = categoryService.getCategoryById(categoryId);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    //create new category by admin
    @PostMapping("/category")
    public ResponseEntity<CategoryDTO> createCategory(@RequestParam("id") int id,
                                                      @RequestParam("categoryName") String categoryName,
                                                      @RequestParam("description") String description,
                                                      @RequestParam("imageUrl") String imageUrl)    {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(id);
        categoryDTO.setCategoryName(categoryName);
        categoryDTO.setDescription(description);
        categoryDTO.setImageUrl(imageUrl);
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    //update a category
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestParam("categoryName") String categoryName,
            @RequestParam("description") String description) {

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(categoryName);
        categoryDTO.setDescription(description);
        CategoryDTO updatedCategory = categoryService.updateCategory(categoryId, categoryDTO);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build();
    }

    // Delete a category
    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().body("{\"message\": \"Category deleted successfully\"}");
    }

}
