package com.khan.studentmanagement.service;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secret;
	
	public String generateToken(String username, String role) {

        SecretKey key = Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(secret)
        );

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 300000))
                .signWith(key)
                .compact();
	}
	
	public String generateRefreshToken(String username) {

	    SecretKey key = Keys.hmacShaKeyFor(
	            Decoders.BASE64.decode(secret));

	    return Jwts.builder()
	            .subject(username)
	            .claim("tokenType","refresh")
	            .issuedAt(new Date())
	            .expiration(new Date(System.currentTimeMillis() + 604800000))
	            .signWith(key)
	            .compact();
	}
	
	public String extractUsername(String token) {

	    SecretKey key = Keys.hmacShaKeyFor(
	            Decoders.BASE64.decode(secret)
	    );

	    return Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload()
	            .getSubject();
	}
	
	public boolean isRefreshToken(String token) {
	    try {
	        SecretKey key = Keys.hmacShaKeyFor(
	                Decoders.BASE64.decode(secret));

	        Claims claims = Jwts.parser()
	                .verifyWith(key)
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();

	        return "refresh".equals(claims.get("tokenType", String.class));

	    } 
	    
	    catch (JwtException | IllegalArgumentException e) {
	        return false;
	    }
	    
	}

	public boolean validateToken(String token, UserDetails userDetails) {

	    String username = extractUsername(token);

	    return username.equals(userDetails.getUsername());
	}
	
	public Date extractExpiration(String token) {

	    SecretKey key = Keys.hmacShaKeyFor(
	            Decoders.BASE64.decode(secret));

	    return Jwts.parser()
	            .verifyWith(key)
	            .build()
	            .parseSignedClaims(token)
	            .getPayload()
	            .getExpiration();
	}
}
