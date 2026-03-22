package com.aimed.aimed.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final JwtProperties jwtProperties;
    private final SecretKey signingKey;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.jwtSecret());
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(Long userId, String role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("type", "access")
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + jwtProperties.accessExpiration()))
                .signWith(signingKey)
                .compact();
    }

    public String generateRefreshToken(Long userId, String role) {
        return Jwts.builder()
                .subject(userId.toString())
                .claim("type", "refresh")
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()
                        + jwtProperties.refreshExpiration()))
                .signWith(signingKey)
                .compact();
    }
}
