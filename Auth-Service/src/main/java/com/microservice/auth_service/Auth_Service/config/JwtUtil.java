package com.microservice.auth_service.Auth_Service.config;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final String SECRET = "my_super_secret_jwt_key_which_is_32_chars_long!";

    private final long ACCESS_EXP = 1000 * 60 * 15;   // 1000 * 60 * 15 -> 15 min , 1000 * 10 -> 10 sec

    public String generateAccessToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .claim("type", "access")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
                .signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public boolean isTokenExpired(String token) {
        return validateToken(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }
    
}