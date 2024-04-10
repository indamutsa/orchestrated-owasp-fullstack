package com.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.backend.DTO.SignUpDto;
import com.example.backend.exceptions.InvalidJwtException;
import com.example.backend.models.User;
import com.example.backend.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username);
        return (UserDetails) user;
    }

    public UserDetails signUp(SignUpDto data) throws InvalidJwtException{
        if (userRepository.findByUsername(data.username()) != null) {
            throw new InvalidJwtException("Username already exists");
        }
        
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.username(), encryptedPassword, data.role());

        return userRepository.save(newUser);
    }
    
}
