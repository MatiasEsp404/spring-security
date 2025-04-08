package com.matias.springjwt.service.abs;

import com.matias.springjwt.dto.request.LoginRequest;
import com.matias.springjwt.dto.request.RegisterRequest;
import com.matias.springjwt.dto.request.TokenRefreshRequest;
import com.matias.springjwt.dto.response.LoginResponse;
import com.matias.springjwt.dto.response.RegisterResponse;
import com.matias.springjwt.dto.response.TokenRefreshResponse;

public interface IAuthService {

    public LoginResponse login(LoginRequest request);

    public RegisterResponse register(RegisterRequest request);

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request);

    public void logout();
}
