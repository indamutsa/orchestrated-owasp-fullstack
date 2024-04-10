package com.example.backend.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.Item;

public interface ItemRepository extends JpaRepository<Item, UUID>{

}
