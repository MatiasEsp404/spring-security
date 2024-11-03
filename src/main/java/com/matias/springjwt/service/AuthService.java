package com.matias.springjwt.service;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.matias.springjwt.dto.request.LoginRequest;
import com.matias.springjwt.dto.request.RegisterRequest;
import com.matias.springjwt.dto.request.TokenRefreshRequest;
import com.matias.springjwt.dto.response.LoginResponse;
import com.matias.springjwt.dto.response.RegisterResponse;
import com.matias.springjwt.dto.response.TokenRefreshResponse;
import com.matias.springjwt.exception.InvalidCredentialsException;
import com.matias.springjwt.exception.TokenRefreshException;
import com.matias.springjwt.exception.UserAlreadyExistException;
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

import jakarta.persistence.EntityNotFoundException;

@Service
public class AuthService implements IAuthService {
	
	@Autowired
    private AuthenticationManager authenticationManager;

	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private IRoleRepository roleRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private RefreshTokenService refreshTokenService;

	@Override
	public RegisterResponse register(RegisterRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new UserAlreadyExistException("Username is already taken.");
		}
		if (userRepository.existsByEmail(request.getUsername())) {
			throw new UserAlreadyExistException("Email is already taken.");
		}
	    RoleEntity role = roleRepository.findByName(ERole.USER)
			.orElseThrow(() -> new EntityNotFoundException("Error: Role is not found."));
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(request, user);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRoles(Collections.singleton(role));
		user = userRepository.save(user);
		RegisterResponse response = new RegisterResponse();
		BeanUtils.copyProperties(user, response);
	    return response;
	}

	@Override
	public LoginResponse login(LoginRequest request) {
		Authentication authentication = authenticationManager
			.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
		String token = jwtUtils.generateJwtToken(user);
		Set<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());
		RefreshTokenEntity refreshToken = refreshTokenService.createRefreshToken(user.getId());
		return LoginResponse.builder()
				.token(token)
				.refreshToken(refreshToken.getToken())
				.roles(roles)
				.build();
	}

	@Override
	public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
	    String refreshToken = request.getRefreshToken();
	    RefreshTokenEntity tokenEntity = refreshTokenService.findByToken(refreshToken)
			.orElseThrow(() -> new TokenRefreshException("Refresh token is not in database."));
	    refreshTokenService.verifyExpiration(tokenEntity);
	    String token = jwtUtils.generateTokenFromUsername(tokenEntity.getUser().getUsername());
	    return new TokenRefreshResponse(token);
	}

	@Override
	public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new InvalidCredentialsException("No user is logged in.");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Integer userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
	}
}
