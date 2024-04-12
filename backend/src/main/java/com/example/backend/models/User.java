package com.example.backend.models;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users", uniqueConstraints = {
           @UniqueConstraint(columnNames = "username"),
           @UniqueConstraint(columnNames = "email")
       })
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 200)
    private String password;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    public User() {
    
    }
    
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    // Getters with lombok
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", email=" + email + "]";
    }
}
