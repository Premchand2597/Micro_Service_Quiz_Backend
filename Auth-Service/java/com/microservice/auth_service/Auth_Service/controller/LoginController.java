package com.microservice.auth_service.Auth_Service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.auth_service.Auth_Service.config.JwtUtil;
import com.microservice.auth_service.Auth_Service.entity.CustomUserDetails;
import com.microservice.auth_service.Auth_Service.entity.LoginCustomResponse;
import com.microservice.auth_service.Auth_Service.entity.LoginEntity;
import com.microservice.auth_service.Auth_Service.entity.LoginRequestDto;
import com.microservice.auth_service.Auth_Service.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@PostMapping("/register")
	public ResponseEntity<LoginEntity> register(@Valid @RequestBody LoginEntity dto) {
		if("".equals(dto.getRole())) {
			dto.setRole("User");
		}
		LoginEntity saveRegistration = loginService.saveRegistration(dto);
		return new ResponseEntity<LoginEntity>(saveRegistration, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto req, HttpServletRequest request, HttpServletResponse response) {

	    try {
	        Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
	        );
	        
	        System.out.println("authentication in login endpoint == "+authentication);
	        
	        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
	        
	        System.out.println("user in login endpoint== "+user);
	        
	        String accessToken = jwtUtil.generateAccessToken(user.getUsername(), user.getAuthorities().iterator().next().getAuthority());
	        
	        System.out.println("accessToken in login endpoint == "+accessToken);
	        
	        LoginCustomResponse res = new LoginCustomResponse(user.getUsername(), accessToken, 
	        		user.getAuthorities().iterator().next().getAuthority());
	        
	        return new ResponseEntity<LoginCustomResponse>(res, HttpStatus.OK);

	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
	    }
	}
}
