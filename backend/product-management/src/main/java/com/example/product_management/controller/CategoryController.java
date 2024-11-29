package com.example.product_management.Controller;

import com.example.product_management.dto.CategoryDTO;
import com.example.product_management.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Get all categories.
     *
     * @return a list of all categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }


    /**
     * Get a category by its ID.
     *
     * @param id the ID of the category
     * @return the category with the specified ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable int id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }

    /**
     * Create a new category.
     *
     * @param categoryDTO the category to create
     *                    - name: the name of the category
     *                    - description: the description of the category
     *                    - imageURL: the URL of the image of the category
     * @return the created category
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO createdCategory = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }


    /**
     * Create a new category.
     *
     * @param categoryDTO the category to create
     *                    - name: the name of the category
     *                    - description: the description of the category
     *                    - imageURL: the URL of the image of the category
     * @return the created category
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable int id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategory = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updatedCategory);
    }

    /**
     * Delete a category by its ID.
     *
     * @param id the ID of the category to delete
     * @return a response entity with no content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
