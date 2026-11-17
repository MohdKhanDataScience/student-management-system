package com.khan.studentmanagement.service;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.khan.studentmanagement.entity.AppUser;
import com.khan.studentmanagement.entity.RefreshToken;
import com.khan.studentmanagement.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

	private RefreshTokenRepository refreshTokenRepository;
	
	private JwtService jwtService;

	public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtService jwtService) {
		super();
		this.refreshTokenRepository = refreshTokenRepository;
		this.jwtService = jwtService;
	}
	
	public RefreshToken createRefreshToken(AppUser user) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByUserId(user.getId())
                .orElse(new RefreshToken());

        refreshToken.setUser(user);
        refreshToken.setToken(
                jwtService.generateRefreshToken(user.getUsername()));
        refreshToken.setExpiryDate(
                LocalDateTime.now().plusDays(7));

        return refreshTokenRepository.save(refreshToken);
    }
	
	public RefreshToken verifyRefreshToken(String token) {

	    RefreshToken refreshToken = refreshTokenRepository
	            .findByToken(token)
	            .orElseThrow(() -> new ResponseStatusException(
	                    HttpStatus.UNAUTHORIZED, "Refresh token not found"));

	    if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {
	        refreshTokenRepository.delete(refreshToken);
	        throw new RuntimeException("Refresh token expired");
	    }

	    if (!jwtService.isRefreshToken(token)) {
	        throw new RuntimeException("Invalid refresh token");
	    }

	    return refreshToken;
	}
	
	public void deleteRefreshToken(String token) {

	    RefreshToken refreshToken = refreshTokenRepository
	            .findByToken(token)
	            .orElseThrow(() -> new RuntimeException("Refresh token not found"));

	    refreshTokenRepository.delete(refreshToken);
	}
}
