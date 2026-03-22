package com.aimed.aimed.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security")
public record SecurityProperties (
        String clientUrl
) {}
