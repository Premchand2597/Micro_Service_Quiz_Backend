package com.microservice.auth_service.Auth_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.microservice.auth_service.Auth_Service.entity.LoginEntity;
import com.microservice.auth_service.Auth_Service.repo.LoginRepo;


@Service
public class LoginService {
	
	@Autowired
	private LoginRepo loginRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public LoginEntity saveRegistration(LoginEntity dto) {
		boolean existsByEmail = loginRepo.existsByEmail(dto.getEmail());
		if(existsByEmail) {
			throw new RuntimeException("Email already registered!");
		}
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));
		dto.setRole("ROLE_"+dto.getRole().trim());
		
		LoginEntity savedData = loginRepo.save(dto);
		return savedData;
	}
}
