package com.aimed.aimed.auth.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.jwt")
public record JwtProperties(
        String jwtSecret,
        Long accessExpiration,
        Long refreshExpiration
) {}