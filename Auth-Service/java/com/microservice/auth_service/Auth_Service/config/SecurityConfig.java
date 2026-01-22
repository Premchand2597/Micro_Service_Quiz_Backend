package com.microservice.auth_service.Auth_Service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.microservice.auth_service.Auth_Service.service.CustomUserDetailsService;


@Configuration
public class SecurityConfig {
	
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	
	@Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
	                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	            )
                .logout(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
                									.anyRequest().authenticated())
                .userDetailsService(customUserDetailsService)
                .build();
    }
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	    return config.getAuthenticationManager();
	}
}
