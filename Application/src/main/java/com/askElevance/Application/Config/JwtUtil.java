package com.askElevance.Application.Config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

//    private final String SECRET = "your_secret_key";
//
//    public String generateToken(String email) {
//        return Jwts.builder()
//                .setSubject(email)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
//                .signWith(SignatureAlgorithm.HS256, SECRET)
//                .compact();
//    }
//
//    public String extractEmail(String token) {
//        return Jwts.parser()
//                .setSigningKey(SECRET)
//                .parseClaimsJws(token)
//                .getBody()
//                .getSubject();
//    }
    private static final long expirationMs = 24 * 60 * 60 * 1000; // 24 hours
    private static final String secret = "S6XxF4vYfYkD7qCVT2AoP8wzMQBz6u5j3sRmGXcqjE0="; // ≥ 64 chars (Base64)

    private final Key key;

    public JwtUtil() {
        // Decode Base64 if needed, otherwise use raw bytes
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(key, SignatureAlgorithm.HS256) // ✅ use Key, not secret
                .compact();
    }

    
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // ✅ use Key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return extractUsername(token).equals(userDetails.getUsername()) &&
                !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // ✅ use Key
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}