package com.example.product_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.product_management.repository.ItemRepository;
import com.example.product_management.model.Item;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public Page<Item> getItems(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }

    public Optional<Item> getItembyId(String id) {
        return itemRepository.findById(id);
    }

    // Display Items by category
    public Page<Item> getItemsByCategory(String categoryId, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return itemRepository.findAll(pageable);
    }
}
