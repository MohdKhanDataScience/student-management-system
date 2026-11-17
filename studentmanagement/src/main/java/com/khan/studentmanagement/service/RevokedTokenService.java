package com.khan.studentmanagement.service;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.khan.studentmanagement.entity.RevokedToken;
import com.khan.studentmanagement.repository.RevokedTokenRepository;

import jakarta.transaction.Transactional;

@Service
public class RevokedTokenService {

	private RevokedTokenRepository revokedTokenRepository;

	public RevokedTokenService(RevokedTokenRepository revokedTokenRepository) {
		super();
		this.revokedTokenRepository = revokedTokenRepository;
	}
	public void revokeToken(String token, LocalDateTime expiryDate) {

        RevokedToken revokedToken = new RevokedToken();

        revokedToken.setToken(token);
        revokedToken.setExpiryDate(expiryDate);

        revokedTokenRepository.save(revokedToken);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokenRepository.existsByToken(token);
    }
    @Transactional
    @Scheduled(fixedRate = 3600000)
    public void deleteExpiredTokens() {

        System.out.println("Scheduler is running...");

        revokedTokenRepository.deleteAllByExpiryDateBefore(
                LocalDateTime.now());

        System.out.println("Expired tokens cleanup completed.");
    }
}
