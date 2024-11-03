package com.matias.springjwt.dto.response;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

	private String token;
	private String refreshToken;
	private Set<String> roles;

}