package com.matias.springjwt.dto.response;

import java.util.List;

public class JwtResponse {

	private String token;
	private String refreshToken;
	private List<String> roles;

	public JwtResponse(String token, String refreshToken, List<String> roles) {
		this.token = token;
		this.refreshToken = refreshToken;
		this.roles = roles;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}