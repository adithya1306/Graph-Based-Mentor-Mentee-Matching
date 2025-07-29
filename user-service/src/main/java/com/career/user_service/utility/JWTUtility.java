package com.career.user_service.utility;

import com.career.user_service.model.User;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

import java.security.Key;

@Component
public class JWTUtility {
    private final String secret = "naanunazhaginiledheivamunarugirenundhanaruginileennaiunarugiren";
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());
    private final long expirationTime = 86400000;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getFirstName())
                .claim("userId", user.getId())
                .claim("role", user.getRole())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }
}