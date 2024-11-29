package com.example.product_management.service;

import com.example.product_management.dto.ItemDTO;
import com.example.product_management.model.Item;
import com.example.product_management.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ItemDTO addItem(ItemDTO itemDTO) {
        Item item = convertToEntity(itemDTO);
        Item savedItem = itemRepository.save(item);
        return convertToDTO(savedItem);
    }

    public ItemDTO updateItem(int itemId, ItemDTO itemDTO) {
        if (itemRepository.existsById(itemId)) {
            Item item = convertToEntity(itemDTO);
            item.setId(itemId);
            Item updatedItem = itemRepository.save(item);
            return convertToDTO(updatedItem);
        }
        return null;
    }

    public void deleteItem(int itemId) {
        itemRepository.deleteById(itemId);
    }

    // entity to DTO
    private ItemDTO convertToDTO(Item item) {
        return new ItemDTO(
                item.getCategoryId(),
                item.getName(),
                item.getDescription(),
                item.getQuantity(),
                item.getSupplier(),
                item.getPrice(),
                null // Image handling depends on implementation
        );
    }

    //DTO to entity
    private Item convertToEntity(ItemDTO dto) {
        Item item = new Item();
        item.setCategoryId(dto.getCategoryId());
        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setQuantity(dto.getStock());
        item.setSupplier(dto.getSupplier());
        item.setPrice(dto.getPrice());
        // Handle image storage logic separately
        return item;
    }
}
