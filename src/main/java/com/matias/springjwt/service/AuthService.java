package com.matias.springjwt.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.matias.springjwt.dto.request.LoginRequest;
import com.matias.springjwt.dto.request.SignupRequest;
import com.matias.springjwt.dto.request.TokenRefreshRequest;
import com.matias.springjwt.dto.response.JwtResponse;
import com.matias.springjwt.dto.response.MessageResponse;
import com.matias.springjwt.dto.response.TokenRefreshResponse;
import com.matias.springjwt.exception.TokenRefreshException;
import com.matias.springjwt.model.RefreshTokenEntity;
import com.matias.springjwt.model.RoleEntity;
import com.matias.springjwt.model.UserEntity;
import com.matias.springjwt.repository.IRoleRepository;
import com.matias.springjwt.repository.IUserRepository;
import com.matias.springjwt.security.dto.ERole;
import com.matias.springjwt.security.dto.UserDetailsImpl;
import com.matias.springjwt.security.jwt.JwtUtils;
import com.matias.springjwt.security.services.RefreshTokenService;
import com.matias.springjwt.service.abs.IAuthService;

@Service
public class AuthService implements IAuthService {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	IUserRepository userRepository;
	
	@Autowired
	IRoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@Autowired
	RefreshTokenService refreshTokenService;

	@Override
	public JwtResponse authenticateUser(LoginRequest request) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(user);
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
		RefreshTokenEntity token = refreshTokenService.createRefreshToken(user.getId());
		return new JwtResponse(jwt, token.getToken(), roles);
	}

	@Override
	public MessageResponse registerUser(SignupRequest request) {
	    if (userRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail())) {
	        return new MessageResponse("Error: Username or Email is already taken!");
	    }
	    RoleEntity role = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
	    UserEntity user = new UserEntity();
	    user.setUsername(request.getUsername());
	    user.setEmail(request.getEmail());
	    user.setPassword(encoder.encode(request.getPassword()));
	    user.setRoles(Collections.singleton(role));
	    userRepository.save(user);
	    return new MessageResponse("User registered successfully!");
	}

	@Override
	public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
	    String refreshToken = request.getRefreshToken();
	    RefreshTokenEntity tokenEntity = refreshTokenService.findByToken(refreshToken).orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
	    refreshTokenService.verifyExpiration(tokenEntity);
	    String token = jwtUtils.generateTokenFromUsername(tokenEntity.getUser().getUsername());
	    return new TokenRefreshResponse(token);
	}

	@Override
	public MessageResponse logoutUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication.getPrincipal().equals("anonymousUser")) {
			return new MessageResponse("No user is logged in or user is anonymous.");
		}
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Integer userId = userDetails.getId();
		refreshTokenService.deleteByUserId(userId);
		return new MessageResponse("Log out successful!");
	}
}
