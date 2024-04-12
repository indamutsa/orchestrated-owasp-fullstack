package com.example.backend.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.example.backend.DTO.ItemIdWrapper;
import com.example.backend.models.Item;
import com.example.backend.service.ItemService;

import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials="true")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping("/count")
    public ResponseEntity<Long> getItemCount() {
        long count = itemService.getItemCount();
        return ResponseEntity.ok().body(count);
    }


    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = itemService.getAllItems();
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItem(@PathVariable("id") UUID id) {
        Item item = itemService.getItemById(id);
        if (item == null)
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // Get items by user id
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Item>> getItemsByUser(@PathVariable UUID userId) {
        List<Item> items = itemService.getItemsByUser(userId);
        return ResponseEntity.ok().body(items);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Item> createItem(@PathVariable UUID userId, @RequestBody Item item) {
        Item createdItem = itemService.createItem(item, userId);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Item> updateItem(@PathVariable UUID userId, @RequestBody Item itemDetails) {
        Item updatedItem = itemService.updateItem(userId, itemDetails);
        if (updatedItem == null)
            return ResponseEntity.notFound().build();
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID userId, @RequestBody ItemIdWrapper itemIdWrapper) {
        UUID itemId = itemIdWrapper.getItemId();
        boolean deleted = itemService.deleteItem(userId, itemId);
        if (!deleted)
            return ResponseEntity.notFound().build();
        return ResponseEntity.noContent().build();
    }
}
