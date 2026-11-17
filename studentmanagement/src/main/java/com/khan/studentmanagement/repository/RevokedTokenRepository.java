package com.khan.studentmanagement.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.khan.studentmanagement.entity.RevokedToken;

public interface RevokedTokenRepository extends JpaRepository<RevokedToken, Long>{
	 
	Optional<RevokedToken> findByToken(String token);

	boolean existsByToken(String token);
	
	void deleteAllByExpiryDateBefore(LocalDateTime now);

}
