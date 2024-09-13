package com.matias.springjwt.dto.response;

public class TokenRefreshResponse {

	private String token;

	public TokenRefreshResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}