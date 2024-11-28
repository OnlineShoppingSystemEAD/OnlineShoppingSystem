package com.example.product_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.example.product_management.repository.ItemRepository;
import com.example.product_management.dto.ItemDTO;
import com.example.product_management.model.Item;
import java.util.Optional;

import org.springframework.web.server.ResponseStatusException;


@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Page<Item> getItems(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> getItembyId(int id) {
        return itemRepository.findById(id);
    }

    // Display Items by category
    public Page<Item> getItemsByCategory(String categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }

    // convert item entity to DTO
    private ItemDTO convertToDTO(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getQuantity(),
                item.getCategoryId()
        );
    }

    // ADMIN SERVICES PART

    // Add Item
    public Item addItem(ItemDTO itemDTO) {
        Item item = convertToEntity(itemDTO);
        return itemRepository.save(item);
    }

    // Update Item
    public Item updateItem(int id, ItemDTO itemDTO) {
        Optional<Item> existingItem = itemRepository.findById(id);
        if (existingItem.isPresent()) {
            Item updatedItem = convertToEntity(itemDTO);
            updatedItem.setId(id);
            return itemRepository.save(updatedItem);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
        }
    }

    // Delete Item
    public void deleteItem(int id) {
        if (itemRepository.existsById(id)) {
            itemRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found with id: " + id);
        }
    }

    //Convert item dto to an entity
    private Item convertToEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        item.setPrice(itemDTO.getPrice());
        item.setQuantity(itemDTO.getQuantity());
        item.setCategoryId(itemDTO.getCategoryId());
        return item;
    }

}

