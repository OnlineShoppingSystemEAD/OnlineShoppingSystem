package com.example.product_management.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.product_management.model.Item;
import com.example.product_management.repository.ItemRepository;

public class ItemServiceTest {
    @Mock
    private ItemRepository underTestItemRepository;
    private AutoCloseable autoCloseable;
    private ItemService undertTestItemService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        undertTestItemService = new ItemService(underTestItemRepository);

    }

    @AfterEach
    void tearDown() throws Exception {
        if (autoCloseable != null) {
            autoCloseable.close();
        }
    }

    @Test
    void testGetItembyId() {
        undertTestItemService.getItembyId(1);
        verify(underTestItemRepository).findById(1);
    }

    @Test
    void testGetItems() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> mockPage = new PageImpl<>(List.of(new Item(), new Item()));
        when(underTestItemRepository.findAll(pageable)).thenReturn(mockPage);
        Page<Item> result = undertTestItemService.getItems(0, 10);
        verify(underTestItemRepository).findAll(pageable);
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void testGetItemsByCategory() {
        int categoryId = 2;
        Pageable pageable = PageRequest.of(0, 10);
        Page<Item> mockPage = new PageImpl<>(List.of(new Item(), new Item()));
        when(underTestItemRepository.findAll(pageable)).thenReturn(mockPage);
        Page<Item> result = undertTestItemService.getItemsByCategory(categoryId, 0, 10);
        verify(underTestItemRepository).findAll(pageable);
        assertNotNull(result);
        assertEquals(2, result.getContent().size());
    }

}
