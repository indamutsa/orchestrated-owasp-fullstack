package com.example.backend;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.backend.models.Item;
import com.example.backend.repository.ItemRepository;
import com.example.backend.service.ItemService;

@SpringBootTest
public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemService itemService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllItems() {
        // Arrange
        List<Item> items = new ArrayList<>();
        items.add(new Item(UUID.randomUUID(), "Item 1", "Description 1"));
        items.add(new Item(UUID.randomUUID(), "Item 2", "Description 2"));
        when(itemRepository.findAll()).thenReturn(items);

        // Act
        List<Item> result = itemService.getAllItems();

        // Assert
        assertEquals(items, result);
    }

    @Test
    public void testGetItemById() {
        // Arrange
        UUID itemId = UUID.randomUUID();
        Item item = new Item(itemId, "Item 1", "Description 1");
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // Act
        Item result = itemService.getItemById(itemId);

        // Assert
        assertEquals(item, result);
    }

    @Test
    public void testGetItemById_NotFound() {
        // Arrange
        UUID itemId = UUID.randomUUID();
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act
        Item result = itemService.getItemById(itemId);

        // Assert
        assertNull(result);
    }

    @Test
    public void testCreateItem() {
        // Arrange
        Item item = new Item(UUID.randomUUID(), "Item 1", "Description 1");
        when(itemRepository.save(item)).thenReturn(item);

        // Act
        Item result = itemService.createItem(item);

        // Assert
        assertEquals(item, result);
    }

    @Test
    public void testUpdateItem() {
        // Arrange
        UUID itemId = UUID.randomUUID();
        Item existingItem = new Item(itemId, "Item 1", "Description 1");
        Item updatedItem = new Item(itemId, "Updated Item", "Updated Description");
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(existingItem));
        when(itemRepository.save(existingItem)).thenReturn(updatedItem);

        // Act
        Item result = itemService.updateItem(itemId, updatedItem);

        // Assert
        assertEquals(updatedItem, result);
        assertEquals("Updated Item", existingItem.getName());
        assertEquals("Updated Description", existingItem.getDescription());
    }

    @Test
    public void testUpdateItem_NotFound() {
        // Arrange
        UUID itemId = UUID.randomUUID();
        Item updatedItem = new Item(itemId, "Updated Item", "Updated Description");
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act
        Item result = itemService.updateItem(itemId, updatedItem);

        // Assert
        assertNull(result);
    }

    @Test
    public void testGetItemCount() {
        // Arrange
        long count = 5;
        when(itemRepository.count()).thenReturn(count);

        // Act
        long result = itemService.getItemCount();

        // Assert
        assertEquals(count, result);
    }

    @Test
    public void testDeleteItem() {
        // Arrange
        UUID itemId = UUID.randomUUID();
        Item item = new Item(itemId, "Item 1", "Description 1");
        when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));

        // Act
        boolean result = itemService.deleteItem(itemId);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testDeleteItem_NotFound() {
        // Arrange
        UUID itemId = UUID.randomUUID();
        when(itemRepository.findById(itemId)).thenReturn(Optional.empty());

        // Act
        boolean result = itemService.deleteItem(itemId);

        // Assert
        assertFalse(result);
    }
}