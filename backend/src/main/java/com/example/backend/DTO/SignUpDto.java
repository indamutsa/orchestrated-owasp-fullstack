package com.example.backend.DTO;

import com.example.backend.models.UserRole;

public record SignUpDto(String username, String password, UserRole role) {
    
}
