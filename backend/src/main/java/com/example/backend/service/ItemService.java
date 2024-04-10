package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.backend.models.Item;
import com.example.backend.repository.ItemRepository;

import java.util.*;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    } 

    public Item getItemById(UUID id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item createItem(Item item) {
        return itemRepository.save(item);
    }
    
    public Item updateItem(UUID id, Item itemDetails) {
        Item item = getItemById(id);
        if (item == null)
            return null;
        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        return itemRepository.save(item);
    }

    public long getItemCount() {
        return itemRepository.count();
    }
    
    public boolean deleteItem(UUID id) {
        Item item = getItemById(id);
        if (item == null)
            return false;
        itemRepository.delete(item);
        return true;
    }
}

