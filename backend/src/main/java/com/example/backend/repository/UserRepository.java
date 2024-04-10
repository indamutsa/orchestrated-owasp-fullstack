package com.example.backend.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.User;

import org.springframework.security.core.userdetails.UserDetails;




public interface UserRepository extends JpaRepository<User, UUID>{

    UserDetails findByUsername(String username);
    // UserDetails findByUsername(String username);
}
