package com.khan.studentmanagement.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.khan.studentmanagement.entity.AppUser;
import com.khan.studentmanagement.repository.AppUserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		AppUser appUser = appUserRepository.findByUsername(username)
		        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
		
		return User.builder()
				.username(appUser.getUsername())
				.password(appUser.getPassword())
				.roles(appUser.getRole())
				.build();
	}
	
}
