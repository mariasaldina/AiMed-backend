package com.aimed.aimed.ollama;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ollama")
public record OllamaProperties(
        String url,
        String defaultModel,
        boolean defaultStream,
        String apiKey
) {}