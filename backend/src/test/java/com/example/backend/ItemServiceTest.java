package com.example.backend;

import com.example.backend.models.Item;
import com.example.backend.models.User;
import com.example.backend.repository.ItemRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.ItemService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ItemService itemService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllItems() {
        when(itemRepository.findAll()).thenReturn(new ArrayList<>());
        List<Item> items = itemService.getAllItems();
        assertNotNull(items);
        verify(itemRepository, times(1)).findAll();
    }

    @Test
    public void testGetItemById() {
        UUID id = UUID.randomUUID();
        Item item = new Item();
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        Item foundItem = itemService.getItemById(id);
        assertNotNull(foundItem);
        assertEquals(item, foundItem);
        verify(itemRepository, times(1)).findById(id);
    }

    @Test
    public void testCreateItem() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        Item item = new Item();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRepository.save(item)).thenReturn(item);
        Item createdItem = itemService.createItem(item, userId);
        assertNotNull(createdItem);
        assertEquals(item, createdItem);
        verify(userRepository, times(1)).findById(userId);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void testUpdateItem() {
        UUID id = UUID.randomUUID();
        Item item = new Item();
        Item itemDetails = new Item();
        itemDetails.setName("New Name");
        itemDetails.setDescription("New Description");
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        when(itemRepository.save(item)).thenReturn(item);
        Item updatedItem = itemService.updateItem(id, itemDetails);
        assertNotNull(updatedItem);
        assertEquals(itemDetails.getName(), updatedItem.getName());
        assertEquals(itemDetails.getDescription(), updatedItem.getDescription());
        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, times(1)).save(item);
    }

    @Test
    public void testGetItemCount() {
        when(itemRepository.count()).thenReturn(5L);
        long count = itemService.getItemCount();
        assertEquals(5L, count);
        verify(itemRepository, times(1)).count();
    }

    @Test
    public void testDeleteItem() {
        UUID id = UUID.randomUUID();
        Item item = new Item();
        when(itemRepository.findById(id)).thenReturn(Optional.of(item));
        doNothing().when(itemRepository).delete(item);
        boolean isDeleted = itemService.deleteItem(id);
        assertTrue(isDeleted);
        verify(itemRepository, times(1)).findById(id);
        verify(itemRepository, times(1)).delete(item);
    }

    @Test
    public void testGetItemsByUser() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        List<Item> items = new ArrayList<>();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(itemRepository.findByUser(user)).thenReturn(items);
        List<Item> foundItems = itemService.getItemsByUser(userId);
        assertNotNull(foundItems);
        assertEquals(items, foundItems);
        verify(userRepository, times(1)).findById(userId);
        verify(itemRepository, times(1)).findByUser(user);
    }
}