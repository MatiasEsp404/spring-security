package com.matias.springjwt.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matias.springjwt.dto.request.LoginRequest;
import com.matias.springjwt.dto.request.SignupRequest;
import com.matias.springjwt.dto.request.TokenRefreshRequest;
import com.matias.springjwt.dto.response.JwtResponse;
import com.matias.springjwt.dto.response.MessageResponse;
import com.matias.springjwt.dto.response.TokenRefreshResponse;
import com.matias.springjwt.service.abs.IAuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private IAuthService authService;

	@PostMapping("/sign-up")
	public ResponseEntity<MessageResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return ResponseEntity.ok(authService.registerUser(signUpRequest));
	}

	@PostMapping("/sign-in")
	public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authService.authenticateUser(loginRequest));
	}

	@PostMapping("/refresh-token")
	public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
		return ResponseEntity.ok(authService.refreshToken(request));
	}

	@PostMapping("/sign-out")
	public ResponseEntity<MessageResponse> logoutUser() {
		return ResponseEntity.ok(authService.logoutUser());
	}

}