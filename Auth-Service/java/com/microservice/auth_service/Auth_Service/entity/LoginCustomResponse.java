package com.microservice.auth_service.Auth_Service.entity;

public class LoginCustomResponse {

	private String email;
	private String accessToken;
	private String role;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "LoginCustomResponse [email=" + email + ", accessToken=" + accessToken + ", role=" + role + "]";
	}
	public LoginCustomResponse(String email, String accessToken, String role) {
		super();
		this.email = email;
		this.accessToken = accessToken;
		this.role = role;
	}
	public LoginCustomResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
}
