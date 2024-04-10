package com.example.backend.models;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "refreshtoken")
public class RefreshToken {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  private User user;

  @Column(nullable = false, unique = true)
  private String token;

  @Column(nullable = false)
  private Instant expiryDate;

  //getters and setters
  public UUID getId() {
      return id;
  }
    
  public void setId(UUID id) {
      this.id = id;
  }
    
  public User getUser() {
      return user;
  }
    
  public void setUser(User user) {
      this.user = user;
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
                ", user=" + user +
                ", token='" + token + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}