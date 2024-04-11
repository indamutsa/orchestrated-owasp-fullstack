package com.example.backend.security;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.exceptions.TokenRefreshException;
import com.example.backend.models.RefreshToken;
import com.example.backend.models.User;
import com.example.backend.repository.RefreshTokenRepository;
import com.example.backend.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
  @Value("${backend.app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private UserRepository userRepository;

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenRepository.findByToken(token);
  }

  public RefreshToken createRefreshToken(UUID userId) {
      User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User Not Found with id: " + userId));

      // Delete any existing refresh token for the user
      refreshTokenRepository.findById(user.getId())
          .ifPresent(existingToken -> refreshTokenRepository.delete(existingToken));

      // Create a new refresh token
      RefreshToken refreshToken = new RefreshToken();
      refreshToken.setUser(user);
      refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
      refreshToken.setToken(UUID.randomUUID().toString());

      // Save the new token in the database
      refreshToken = refreshTokenRepository.save(refreshToken);

      return refreshToken;
  }

  public RefreshToken verifyExpiration(RefreshToken token) {
    if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(token);
      throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
    }

    return token;
  }

  public void invalidateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
            .ifPresent(refreshTokenRepository::delete);
    }

  @Transactional
  public int deleteByUserId(UUID userId) {
     return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
  }
}