package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.models.Item;
import com.example.backend.models.User;
import com.example.backend.repository.ItemRepository;
import com.example.backend.repository.UserRepository;

import jakarta.transaction.Transactional;

import java.util.*;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    } 

    public Item getItemById(UUID id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item getItemByUserId(UUID id, UUID itemId) {
        Item item = getItemById(itemId);
        if (item == null || !item.getUser().getId().equals(id))
            return null;
        
        return item;
    }

    public Item createItem(Item item, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        item.setUser(user);
        return itemRepository.save(item);
    }
    
    @Transactional
    public Item updateItem(UUID userId, Item itemDetails) {
        try {
            Item item = getItemByUserId(userId, itemDetails.getId());
                        
            item.setName(itemDetails.getName());
            item.setDescription(itemDetails.getDescription());
            
            return itemRepository.save(item);
        } catch (Exception e) {
            System.out.println("Error during transaction: " + e.getMessage());
            throw e; 
        }
    }

    public long getItemCount() {
        return itemRepository.count();
    }
    
    public boolean deleteItem(UUID id, UUID itemId) {
        try {
            Item item = getItemByUserId(id, itemId);                
            itemRepository.delete(item);
            return true;
        } catch (Exception e) {
            System.out.println("Error during transaction: " + e.getMessage());
            throw e; 
        }
    }

    public List<Item> getItemsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Error: User not found."));
        return itemRepository.findByUser(user);
    }
}

