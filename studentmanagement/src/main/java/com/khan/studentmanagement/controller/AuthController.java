package com.khan.studentmanagement.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.khan.studentmanagement.dto.AuthResponse;
import com.khan.studentmanagement.entity.AppUser;
import com.khan.studentmanagement.entity.RefreshToken;
import com.khan.studentmanagement.repository.AppUserRepository;
import com.khan.studentmanagement.service.AppUserService;
import com.khan.studentmanagement.service.CustomUserDetailsService;
import com.khan.studentmanagement.service.JwtService;
import com.khan.studentmanagement.service.RefreshTokenService;
import com.khan.studentmanagement.service.RevokedTokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AppUserService appUserService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private AppUserRepository appUserRepository;
	
	@Autowired
	private RefreshTokenService refreshTokenService;
	
	@Autowired
	private RevokedTokenService revokedTokenService;
	
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody AppUser appUser){
		appUserService.registerUser(appUser);
		
		return ResponseEntity.ok("User Register successfully");
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody AppUser appUser) {

	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(
	                    appUser.getUsername(), appUser.getPassword()));

	    String username = authentication.getName();

	    String role = authentication.getAuthorities().iterator().next()
	            .getAuthority().replace("ROLE_", "");

	    String accessToken = jwtService.generateToken(username, role);
	    System.out.println("LOGIN TOKEN: " + accessToken);

	    AppUser user = appUserRepository.findByUsername(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    RefreshToken refreshToken =
	            refreshTokenService.createRefreshToken(user);

	    return ResponseEntity.ok(
	            new AuthResponse(accessToken, refreshToken.getToken()));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refreshToken(
	        @RequestBody Map<String, String> request) {

	    String token = request.get("refreshToken");

	    RefreshToken verifiedToken =
	            refreshTokenService.verifyRefreshToken(token);

	    AppUser user = verifiedToken.getUser();

	    UserDetails userDetails =
	            customUserDetailsService.loadUserByUsername(user.getUsername());

	    String role = userDetails.getAuthorities().iterator().next()
	            .getAuthority().replace("ROLE_", "");

	    String newAccessToken =
	            jwtService.generateToken(user.getUsername(), role);

	    RefreshToken newRefreshToken =
	            refreshTokenService.createRefreshToken(user);

	    return ResponseEntity.ok(
	            new AuthResponse(newAccessToken, newRefreshToken.getToken()));
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(
	        @RequestHeader("Authorization") String authHeader,
	        @RequestBody Map<String, String> request) {

		String accessToken = authHeader.substring(7).replace("\"", "").trim();
	    System.out.println("LOGOUT TOKEN: " + accessToken);
	    String refreshToken = request.get("refreshToken");

	    System.out.println("Step 1: Logout started");

	    Date expiryDate = jwtService.extractExpiration(accessToken);
	    System.out.println("Step 2: Access token validated");

	    LocalDateTime expiry = expiryDate.toInstant()
	            .atZone(ZoneId.systemDefault())
	            .toLocalDateTime();

	    revokedTokenService.revokeToken(accessToken, expiry);
	    System.out.println("Step 3: Access token revoked");

	    refreshTokenService.deleteRefreshToken(refreshToken);
	    System.out.println("Step 4: Refresh token deleted");

	    return ResponseEntity.ok("Logged out successfully");
	}
	
}
