package com.example.backend.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.Item;
import com.example.backend.models.User;

public interface ItemRepository extends JpaRepository<Item, UUID>{
    List<Item> findByUser(User user);
    Optional<Item> findByUserId(UUID id);
}
