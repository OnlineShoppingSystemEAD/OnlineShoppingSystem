package com.example.product_management.service;

import com.example.product_management.dto.CategoryDTO;
import com.example.product_management.model.Category;
import com.example.product_management.repository.CategoryRepository;
import com.example.product_management.exception.CategoryNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryDTO> getAllCategories() {
        try{
            return categoryRepository.findAll().stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        }catch (Exception e){
            System.out.println("Error: " + e);
            return null;
        }
    }

    public CategoryDTO getCategoryById(int id) {
        try{
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isEmpty()) {
                throw new CategoryNotFoundException("Category not found with id: " + id);
            }
            return convertToDTO(category.get());
        }catch (Exception e){
            System.out.println("Error: " + e);
            return null;
        }
    }

    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        try {
            Category category = new Category();
            category.setName(categoryDTO.getName());
            category.setDescription(categoryDTO.getDescription());
            category.setImageURL(categoryDTO.getImageURL());
            Category savedCategory = categoryRepository.save(category);
            return convertToDTO(savedCategory);
        }catch (Exception e){
            System.out.println("Error: " + e);
            return null;
        }

    }

    public CategoryDTO updateCategory(int id, CategoryDTO categoryDTO) {
        try{
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isEmpty()) {
                throw new CategoryNotFoundException("Category not found with id: " + id);
            }
            Category categoryToUpdate = category.get();

            if(categoryDTO.getName() != null){
                categoryToUpdate.setName(categoryDTO.getName());
            }
            if(categoryDTO.getDescription() != null){
                categoryToUpdate.setDescription(categoryDTO.getDescription());
            }
            if(categoryDTO.getImageURL() != null){
                categoryToUpdate.setImageURL(categoryDTO.getImageURL());
            }

            Category updatedCategory = categoryRepository.save(categoryToUpdate);
            return convertToDTO(updatedCategory);
        }catch (Exception e){
            System.out.println("Error: " + e);
            return null;
        }

    }

    public void deleteCategory(int id) {
        try{
            Optional<Category> category = categoryRepository.findById(id);
            if (category.isEmpty()) {
                throw new CategoryNotFoundException("Category not found with id: " + id);
            }
            categoryRepository.deleteById(id);
        }catch (Exception e){
            System.out.println("Error: " + e);
        }
    }

    private CategoryDTO convertToDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setImageURL(category.getImageURL());
        return dto;
    }

    private Category convertToEntity(CategoryDTO dto) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setImageURL(dto.getImageURL());
        return category;
    }
}