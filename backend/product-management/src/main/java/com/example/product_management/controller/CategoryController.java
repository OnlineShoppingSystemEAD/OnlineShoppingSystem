package com.example.product_management.controller;

import com.example.product_management.dto.CategoryDTO;
import com.example.product_management.dto.ResponseDTO;
import com.example.product_management.service.AmazonS3Service;
import com.example.product_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @GetMapping
    public ResponseEntity<ResponseDTO<CategoryDTO>> getAllCategories() {
        ResponseDTO<CategoryDTO> response = categoryService.getAllCategories();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> getCategoryById(@PathVariable int id) {
        ResponseDTO<CategoryDTO> response = categoryService.getCategoryById(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseDTO<CategoryDTO>> createCategory(
            @RequestPart("category") CategoryDTO categoryDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestParam Integer userId,
            @RequestParam String role
    ) throws IOException {
        if (image != null) {
            String imageUrl = amazonS3Service.uploadFile(image, categoryDTO.getId());
            categoryDTO.setImageURL(imageUrl);
        }
        ResponseDTO<CategoryDTO> response = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<ResponseDTO<CategoryDTO>> updateCategory(
            @PathVariable int id,
            @RequestPart(value = "category", required = false) CategoryDTO categoryDTO,
            @RequestPart(value = "image", required = false) MultipartFile image,
            @RequestParam Integer userId,
            @RequestParam String role
    ) throws IOException {
        System.out.println("userId: " + userId);
        System.out.println("role: " + role);
        if (image != null) {
            String imageUrl = amazonS3Service.uploadFile(image, categoryDTO.getId());
            categoryDTO.setImageURL(imageUrl);
        }
        ResponseDTO<CategoryDTO> response = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<CategoryDTO>> deleteCategory(
            @PathVariable int id,
            @RequestParam Integer userId,
            @RequestParam String role
    ) {
        ResponseDTO<CategoryDTO> response = categoryService.deleteCategory(id);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatus()));
    }
}