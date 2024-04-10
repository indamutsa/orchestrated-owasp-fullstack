package com.example.backend.DTO;

import java.util.List;
import java.util.UUID;

public class UserInfoResponse {
	private UUID id;
	private String username;
	private String email;
	private List<String> roles;
	private String token;
	private String type = "Bearer";
	private String refreshToken;

	public UserInfoResponse(String accessToken, String refreshToken, UUID uuid, String username, String email,
			List<String> roles) {
		this.token = accessToken;
		this.refreshToken = refreshToken;
		this.id = uuid;
		this.username = username;
		this.email = email;
		this.roles = roles;

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String toString() {
		return "UserInfoResponse [id=" + id + ", username=" + username + ", email=" + email + ", roles=" + roles
				+ ", token=" + token + ", type=" + type + ", refreshToken=" + refreshToken + "]";
	}

}
