package com.example.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.backend.exceptions.TokenRefreshException;
import com.example.backend.models.RefreshToken;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Service
public class RefreshTokenService {
  @Value("${backend.app.jwtRefreshExpirationMs}")
  private Long refreshTokenDurationMs;

  private final String FILE_PATH = "refreshTokens.json";
  private final Map<UUID, LinkedList<RefreshToken>> refreshTokenStore = new ConcurrentHashMap<>();
  private final ObjectMapper mapper;

    public RefreshTokenService() {
        mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());  // Register JSR-310 datetime module
        loadTokensFromFile();
    }

  private void loadTokensFromFile() {
      File file = new File(FILE_PATH);
      if (file.exists()) {
          try {
              List<RefreshToken> tokens = mapper.readValue(file, new TypeReference<List<RefreshToken>>(){});
              tokens.forEach(token -> {
                  token.setExpiryDate(Instant.parse(token.getExpiryDate().toString()));
                  UUID userId = token.getUserId();
                  LinkedList<RefreshToken> userTokens = refreshTokenStore.getOrDefault(userId, new LinkedList<>());
                  userTokens.add(token);
                  refreshTokenStore.put(userId, userTokens);
              });
          } catch (IOException e) {
              e.printStackTrace();
              // If the file is not well-formed, delete it so a new one can be created
              file.delete();
          }
      }
  }

  private void saveTokensToFile() {
    List<RefreshToken> tokens = refreshTokenStore.values().stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
    try {
      mapper.writeValue(new File(FILE_PATH), tokens);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<RefreshToken> getAllRefreshTokens() {
    return refreshTokenStore.values().stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList());
  }

  public Optional<RefreshToken> findByToken(String token) {
    return refreshTokenStore.values().stream()
        .flatMap(Collection::stream)
        .filter(refreshToken -> refreshToken.getToken().equals(token))
        .findFirst();
  }

  public RefreshToken createRefreshToken(UUID userId) {
      RefreshToken refreshToken = new RefreshToken();
      refreshToken.setUserId(userId);
      refreshToken.setExpiryDate(Instant.now().plus(1, ChronoUnit.DAYS)); // set expiry date to a day from now
      refreshToken.setToken(UUID.randomUUID().toString());

      LinkedList<RefreshToken> userTokens = refreshTokenStore.getOrDefault(userId, new LinkedList<>());
      if (userTokens.size() >= 10) {
          userTokens.removeFirst(); // remove the oldest token
      }
      userTokens.add(refreshToken); // add the new token

      refreshTokenStore.put(userId, userTokens);
      saveTokensToFile();

      return refreshToken;
  }

  public boolean verifyExpiration(RefreshToken token) {
      if (Instant.now().isAfter(token.getExpiryDate())) {
          UUID userId = token.getUserId();
          refreshTokenStore.get(userId).removeIf(t -> t.getToken().equals(token.getToken()));
          saveTokensToFile();
          throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
      }

      return true;
  }

  public void invalidateRefreshToken(String token) {
    refreshTokenStore.values().stream()
        .flatMap(Collection::stream)
        .filter(refreshToken -> refreshToken.getToken().equals(token))
        .forEach(this::verifyExpiration);
  }

  public int deleteByUserId(UUID userId) {
    int result = refreshTokenStore.remove(userId) != null ? 1 : 0;
    saveTokensToFile();
    return result;
  }
}