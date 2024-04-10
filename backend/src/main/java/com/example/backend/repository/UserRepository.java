package com.example.backend.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.User;

public interface UserRepository extends JpaRepository<User, UUID>{
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
