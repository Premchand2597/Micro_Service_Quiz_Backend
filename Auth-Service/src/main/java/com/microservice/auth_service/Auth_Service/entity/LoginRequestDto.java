package com.microservice.auth_service.Auth_Service.entity;

import jakarta.validation.constraints.NotEmpty;

public class LoginRequestDto {
	@NotEmpty
	private String email;
	@NotEmpty
    private String password;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginRequestDto [email=" + email + ", password=" + password + "]";
	}
	
}
