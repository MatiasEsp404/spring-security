package com.matias.springjwt.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class JwtResponse {

	private String token;
	private String refreshToken;
	private List<String> roles;

}