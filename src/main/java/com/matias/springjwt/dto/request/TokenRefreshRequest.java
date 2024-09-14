package com.matias.springjwt.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshRequest {

	@NotBlank
	private String refreshToken;

}