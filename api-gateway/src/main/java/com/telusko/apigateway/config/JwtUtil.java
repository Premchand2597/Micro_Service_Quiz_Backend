package com.telusko.apigateway.config;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {

    private final String SECRET = "my_super_secret_jwt_key_which_is_32_chars_long!";

    public Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}