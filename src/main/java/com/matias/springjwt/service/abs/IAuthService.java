package com.matias.springjwt.service.abs;

import com.matias.springjwt.dto.request.LoginRequest;
import com.matias.springjwt.dto.request.SignupRequest;
import com.matias.springjwt.dto.request.TokenRefreshRequest;
import com.matias.springjwt.dto.response.JwtResponse;
import com.matias.springjwt.dto.response.MessageResponse;
import com.matias.springjwt.dto.response.TokenRefreshResponse;

public interface IAuthService {

	public JwtResponse authenticateUser(LoginRequest loginRequest);

	public MessageResponse registerUser(SignupRequest signUpRequest);

	public TokenRefreshResponse refreshToken(TokenRefreshRequest request);

	public MessageResponse logoutUser();
}
