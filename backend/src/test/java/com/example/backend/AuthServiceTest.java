package com.example.backend;

import com.example.backend.models.ERole;
import com.example.backend.models.User;
import com.example.backend.repository.RoleRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        String username = "testuser";
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> authService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "testuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    public void testExistsByUsername() {
        String username = "testuser";
        when(userRepository.existsByUsername(username)).thenReturn(true);

        assertTrue(authService.existsByUsername(username));
        verify(userRepository, times(1)).existsByUsername(username);
    }

    @Test
    public void testExistsByEmail() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertTrue(authService.existsByEmail(email));
        verify(userRepository, times(1)).existsByEmail(email);
    }

    @Test
    public void testFindRoleByName() {
        String roleName = "ROLE_USER";
        when(roleRepository.findByName(ERole.valueOf(roleName))).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.findRoleByName(ERole.valueOf(roleName)));
        verify(roleRepository, times(1)).findByName(ERole.valueOf(roleName));
    }

    @Test
    public void testGetUserAsJson() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> authService.getUserAsJson(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserAsJson_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.getUserAsJson(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testSaveUser() {
        User user = new User();

        assertDoesNotThrow(() -> authService.saveUser(user));
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateTokenIssuedAt() {
        UUID userId = UUID.randomUUID();
        Instant issuedAt = Instant.now();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> authService.updateTokenIssuedAt(userId, issuedAt));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testUpdateTokenIssuedAt_UserNotFound() {
        UUID userId = UUID.randomUUID();
        Instant issuedAt = Instant.now();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.updateTokenIssuedAt(userId, issuedAt));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById() {
        UUID userId = UUID.randomUUID();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> authService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUserById_UserNotFound() {
        UUID userId = UUID.randomUUID();
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.getUserById(userId));
        verify(userRepository, times(1)).findById(userId);
    }
}