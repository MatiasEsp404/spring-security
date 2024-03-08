package com.matias.springjwt.service.abs;

import com.matias.springjwt.dto.request.LoginRequest;
import com.matias.springjwt.dto.request.SignupRequest;
import com.matias.springjwt.dto.request.TokenRefreshRequest;
import com.matias.springjwt.dto.response.JwtResponse;
import com.matias.springjwt.dto.response.MessageResponse;
import org.springframework.http.ResponseEntity;

public interface IAuthService {

    public JwtResponse authenticateUser(LoginRequest loginRequest);

    public Object registerUser(SignupRequest signUpRequest);

    public ResponseEntity<?> refreshtoken(TokenRefreshRequest request);

    public MessageResponse logoutUser();
}
