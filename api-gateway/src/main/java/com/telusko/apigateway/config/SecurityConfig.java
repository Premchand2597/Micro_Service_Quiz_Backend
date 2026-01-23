package com.telusko.apigateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import com.telusko.apigateway.exception.CustomAccessDeniedHandler;
import com.telusko.apigateway.exception.CustomAuthEntryPoint;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
	
	@Autowired
	private JwtWebFilter jwtWebFilter;
	
	@Autowired
	private CustomAuthEntryPoint customAuthEntryPoint;
	
	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityWebFilterChain security(ServerHttpSecurity http) {

        return http
            .csrf(csrf -> csrf.disable())
            .httpBasic(basic -> basic.disable())
            .formLogin(form -> form.disable())
            .authorizeExchange(ex -> ex
                .pathMatchers("/api/auth/**").permitAll()
                .pathMatchers("/quiz/**").hasAnyRole("User","Admin")
                .pathMatchers("/question/**").hasRole("Admin")
                .anyExchange().authenticated()
            )
            .exceptionHandling(ex -> ex
            		.authenticationEntryPoint(customAuthEntryPoint)
            		.accessDeniedHandler(customAccessDeniedHandler)
            )
            .addFilterBefore(jwtWebFilter, SecurityWebFiltersOrder.AUTHORIZATION)
            .build();
    }
}
