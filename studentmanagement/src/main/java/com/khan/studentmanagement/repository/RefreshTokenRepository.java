package com.khan.studentmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khan.studentmanagement.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
	Optional<RefreshToken> findByToken(String token);
	
	Optional<RefreshToken> findByUserId(Long userId);
	
	void deleteByUserId(Long userId);

}
