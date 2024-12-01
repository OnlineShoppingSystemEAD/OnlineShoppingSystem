package com.example.product_management.service;

import com.example.product_management.dto.ItemDTO;
import com.example.product_management.dto.ResponseDTO;
import com.example.product_management.exception.ItemNotFoundException;
import com.example.product_management.model.Category;
import com.example.product_management.model.Item;
import com.example.product_management.repository.CategoryRepository;
import com.example.product_management.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Get all items with pagination support.
     *
     * @param pageNo the page number
     * @param pageSize the page size
     * @return a response DTO containing a list of items
     */
    public ResponseDTO<ItemDTO> getItems(int pageNo, int pageSize) {
        ResponseDTO<ItemDTO> response = new ResponseDTO<>();
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Item> items = itemRepository.findAll(pageable);
            List<ItemDTO> itemDTOs = items.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            setResponseDetails(response, 200, "Success", itemDTOs);
        } catch (Exception e) {
            setResponseDetails(response, 500, "Error: " + e.getMessage(), null);
        }
        return response;
    }

    /**
     * Get an item by its ID.
     *
     * @param id the ID of the item
     * @return a response DTO containing the item
     */
    public ResponseDTO<ItemDTO> getItemById(int id) {
        ResponseDTO<ItemDTO> response = new ResponseDTO<>();
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isEmpty()) {
                throw new ItemNotFoundException("Item not found with id: " + id);
            }
            setResponseDetails(response, 200, "Success", convertToDTO(item.get()));
        } catch (Exception e) {
            setResponseDetails(response, 500, "Error: " + e.getMessage(), null);
        }
        return response;
    }

    /**
     * Get items by category ID with pagination support.
     *
     * @param categoryId the ID of the category
     * @param pageNo the page number
     * @param pageSize the page size
     * @return a response DTO containing a list of items in the specified category
     */
    public ResponseDTO<ItemDTO> getItemsByCategory(int categoryId, int pageNo, int pageSize) {
        ResponseDTO<ItemDTO> response = new ResponseDTO<>();
        try {
            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Item> items = itemRepository.findByCategoryId(categoryId, pageable);
            List<ItemDTO> itemDTOs = items.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
            setResponseDetails(response, 200, "Success", itemDTOs);
        } catch (Exception e) {
            setResponseDetails(response, 500, "Error: " + e.getMessage(), null);
        }
        return response;
    }

    /**
     * Add a new item to a category.
     *
     * @param itemDTO the item to add
     * @param image the image of the item
     * @return a response DTO containing the added item
     * @throws IOException if an I/O error occurs
     */
    public ResponseDTO<ItemDTO> addItemToCategory(ItemDTO itemDTO, MultipartFile image) throws IOException {
        ResponseDTO<ItemDTO> response = new ResponseDTO<>();
        try {
            Item newItem = new Item();
            if (image != null) {
                String imageUrl = amazonS3Service.uploadFile(image, 123);
                newItem.setImageURL(imageUrl);
            }
            Category category = categoryRepository.findById(itemDTO.getCategoryId())
                    .orElseThrow(() -> new ItemNotFoundException("Category not found with id: " + itemDTO.getCategoryId()));
            newItem.setName(itemDTO.getName());
            newItem.setPrice(itemDTO.getPrice());
            newItem.setCategory(category);
            newItem.setQuantity(itemDTO.getQuantity());
            newItem.setDescription(itemDTO.getDescription());
            Item savedItem = itemRepository.save(newItem);
            setResponseDetails(response, 201, "Item created successfully", convertToDTO(savedItem));
        } catch (Exception e) {
            setResponseDetails(response, 500, "Error: " + e.getMessage(), null);
        }
        return response;
    }

    /**
     * Update an existing item.
     *
     * @param id the ID of the item to update
     * @param itemDTO the updated item details
     * @param image the updated image of the item
     * @return a response DTO containing the updated item
     * @throws IOException if an I/O error occurs
     */
    public ResponseDTO<ItemDTO> updateItem(int id, ItemDTO itemDTO, MultipartFile image) throws IOException {
        ResponseDTO<ItemDTO> response = new ResponseDTO<>();
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isEmpty()) {
                throw new ItemNotFoundException("Item not found with id: " + id);
            }
            Item itemToUpdate = item.get();
            if (image != null) {
                String imageUrl = amazonS3Service.uploadFile(image, itemToUpdate.getId());
                itemToUpdate.setImageURL(imageUrl);
            }
            if (itemDTO.getName() != null) {
                itemToUpdate.setName(itemDTO.getName());
            }
            if (itemDTO.getPrice() != null) {
                itemToUpdate.setPrice(itemDTO.getPrice());
            }
            if (itemDTO.getQuantity() != null) {
                itemToUpdate.setQuantity(itemDTO.getQuantity());
            }
            if (itemDTO.getDescription() != null) {
                itemToUpdate.setDescription(itemDTO.getDescription());
            }
            Item updatedItem = itemRepository.save(itemToUpdate);
            setResponseDetails(response, 200, "Item updated successfully", convertToDTO(updatedItem));
        } catch (Exception e) {
            setResponseDetails(response, 500, "Error: " + e.getMessage(), null);
        }
        return response;
    }

    /**
     * Delete an item by its ID.
     *
     * @param id the ID of the item to delete
     * @return a response DTO indicating the result of the deletion
     */
    public ResponseDTO<ItemDTO> deleteItem(int id) {
        ResponseDTO<ItemDTO> response = new ResponseDTO<>();
        try {
            Optional<Item> item = itemRepository.findById(id);
            if (item.isEmpty()) {
                throw new ItemNotFoundException("Item not found with id: " + id);
            }
            itemRepository.deleteById(id);
            setResponseDetails(response, 200, "Item deleted successfully", null);
        } catch (Exception e) {
            setResponseDetails(response, 500, "Error: " + e.getMessage(), null);
        }
        return response;
    }

    /**
     * Set the response details.
     *
     * @param response the response DTO
     * @param status the status code
     * @param message the message
     * @param data the data
     */
    private void setResponseDetails(ResponseDTO<ItemDTO> response, int status, String message, Object data) {
        response.setStatus(status);
        response.setMessage(message);
        if (data instanceof List) {
            response.setData((List<ItemDTO>) data);
        } else {
            response.setData((ItemDTO) data);
        }
    }

    /**
     * Convert an Item entity to an ItemDTO.
     *
     * @param item the item entity
     * @return the item DTO
     */
    private ItemDTO convertToDTO(Item item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(item.getId());
        dto.setName(item.getName());
        dto.setPrice(item.getPrice());
        dto.setQuantity(item.getQuantity());
        dto.setImageURL(item.getImageURL());
        dto.setCategoryId(item.getCategory().getId());
        return dto;
    }

    /**
     * Convert an ItemDTO to an Item entity.
     *
     * @param dto the item DTO
     * @return the item entity
     */
    private Item convertToEntity(ItemDTO dto) {
        Item item = new Item();
        item.setName(dto.getName());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ItemNotFoundException("Category not found with id: " + dto.getCategoryId()));
        item.setCategory(category);
        return item;
    }
}