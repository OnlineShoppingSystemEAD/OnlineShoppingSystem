package com.example.product_management.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Long id, Item itemDetails) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));

        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setPrice(itemDetails.getPrice());

        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
