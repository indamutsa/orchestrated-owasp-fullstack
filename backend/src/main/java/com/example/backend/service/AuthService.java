package com.example.backend.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.models.ERole;
import com.example.backend.models.Role;
import com.example.backend.models.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class AuthService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(
            () -> new UsernameNotFoundException("User Not Found with username: " + username)
        );
        return UserDetailsImpl.build(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
    public Role findRoleByName(ERole role) {
        return roleRepository.findByName(role).orElseThrow(
                () -> new RuntimeException("Error: Role is not found."));
    }

    @Transactional
    public String getUserAsJson(UUID userId) throws JsonProcessingException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new ObjectMapper().writeValueAsString(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void updateTokenIssuedAt(UUID userId, Instant issuedAt) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

        userRepository.save(user);
    }
}
