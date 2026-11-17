
package com.khan.studentmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.khan.studentmanagement.entity.AppUser;
import com.khan.studentmanagement.repository.AppUserRepository;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser registerUser(AppUser appUser) {

        if (appUserRepository.findByUsername(appUser.getUsername()).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Username already exists");
        }

        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setRole("USER");

        return appUserRepository.save(appUser);
    }
}
