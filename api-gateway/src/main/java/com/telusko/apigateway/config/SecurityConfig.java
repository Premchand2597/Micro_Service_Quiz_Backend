package com.telusko.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtWebFilter jwtWebFilter;

    @Bean
    public SecurityWebFilterChain security(ServerHttpSecurity http) {

        return http
            .csrf(csrf -> csrf.disable())
            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
            .authorizeExchange(ex -> ex
                .pathMatchers("/api/auth/**").permitAll()
                .pathMatchers("/quiz/**").hasAnyRole("User","Admin")
                .pathMatchers("/question/**").hasRole("Admin")
                .anyExchange().authenticated()
            )
            .addFilterBefore(jwtWebFilter, SecurityWebFiltersOrder.AUTHORIZATION)
            .build();
    }
}
