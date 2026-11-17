package com.khan.studentmanagement;

import java.security.SecureRandom;

import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StudentmanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudentmanagementApplication.class, args);
		
//		byte[] key = new byte[32];
//        new SecureRandom().nextBytes(key);
//
//        String secret = Base64.getEncoder().encodeToString(key);
//
//        System.out.println(secret);
	}

}
