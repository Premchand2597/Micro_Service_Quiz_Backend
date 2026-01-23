package com.telusko.apigateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.google.common.net.HttpHeaders;

import io.jsonwebtoken.Claims;
import reactor.core.publisher.Mono;

@Component
public class JwtWebFilter implements WebFilter {

    @Autowired
    private JwtUtil jwtUtil;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

        // Allow auth APIs without token
        if (path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        // No token → let Spring Security handle it
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
        	
        	return chain.filter(exchange);
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateToken(token);
            
            System.out.println("claims in api-gateway security = "+claims);

            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            
            System.out.println("username in api-gateway security = "+username);
            System.out.println("role in api-gateway security = "+role);

            Authentication auth =
                new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    List.of(new SimpleGrantedAuthority(role))
                );

            return chain.filter(exchange)
                .contextWrite(
                    ReactiveSecurityContextHolder.withAuthentication(auth)
                );

        } catch (Exception e) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
        	
        	return chain.filter(exchange);
        }
	}
}

