package com.example.backend.models;

import java.time.Instant;
import java.util.UUID;

public class RefreshToken {

  private UUID id;
  private UUID userId; // Changed from User user to UUID userId
  private String token;
  private Instant expiryDate;
  private Instant creationDate;

  //getters and setters
  public UUID getId() {
      return id;
  }
    
  public void setId(UUID id) {
      this.id = id;
  }
    
  public UUID getUserId() { // Changed from User getUser() to UUID getUserId()
      return userId;
  }
    
  public void setUserId(UUID userId) { // Changed from void setUser(User user) to void setUserId(UUID userId)
      this.userId = userId;
  }
    
  public String getToken() {
      return token;
  }
    

  public void setToken(String token) {
      this.token = token;
  }
    
  public Instant getExpiryDate() {
      return expiryDate;
  }
    
  public void setExpiryDate(Instant expiryDate) {
      this.expiryDate = expiryDate;
  }

  @Override
    public String toString() {
        return "RefreshToken{" +
                "id=" + id +
                ", userId=" + userId + // Changed from ", user=" + user to ", userId=" + userId
                ", token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}