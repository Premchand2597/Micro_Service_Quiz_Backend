package com.microservice.auth_service.Auth_Service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservice.auth_service.Auth_Service.entity.CustomUserDetails;
import com.microservice.auth_service.Auth_Service.entity.LoginEntity;
import com.microservice.auth_service.Auth_Service.repo.LoginRepo;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private LoginRepo loginRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    LoginEntity user = loginRepo.findByEmail(username)
	        .orElseThrow(() -> new UsernameNotFoundException("User not found !!"));
	    return new CustomUserDetails(user);
	}

}