package com.khan.studentmanagement.dto;

public class AuthResponse {
	
	private String accessToken;
	private String refreshToken;
	
	public AuthResponse(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}
	

}
