package com.matias.springjwt.security.services;

import com.matias.springjwt.exception.TokenRefreshException;
import com.matias.springjwt.model.RefreshTokenEntity;
import com.matias.springjwt.model.UserEntity;
import com.matias.springjwt.repository.IRefreshTokenRepository;
import com.matias.springjwt.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

	@Value("${springjwt.app.jwtRefreshExpirationMs}")
	private Long refreshTokenDurationMs;

	@Autowired
	private IRefreshTokenRepository refreshTokenRepository;

	@Autowired
	private IUserRepository userRepository;

	public Optional<RefreshTokenEntity> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshTokenEntity createRefreshToken(Long userId) {
		RefreshTokenEntity refreshToken = new RefreshTokenEntity();
		refreshToken.setUser(userRepository.findById(userId).get());
		refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshTokenEntity verifyExpiration(RefreshTokenEntity token) {
		if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
			refreshTokenRepository.delete(token);
			throw new TokenRefreshException(token.getToken(),
					"Refresh token was expired. Please make a new signin request");
		}
		return token;
	}

	@Transactional
	public int deleteByUserId(Long userId) {
		Optional<UserEntity> optionalUserEntity = userRepository.findById(userId);
		if (optionalUserEntity.isEmpty()) {
			throw new EntityNotFoundException("Entity not found");
		}
		return refreshTokenRepository.deleteByUser(userRepository.findById(userId).get());
	}

}