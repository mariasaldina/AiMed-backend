package com.aimed.aimed.ollama;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@EnableConfigurationProperties(OllamaProperties.class)
public class OllamaConfig {

    @Bean
    public RestClient ollamaClient(OllamaProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.url())
                .defaultHeader("Authorization", "Bearer " + properties.apiKey())
                .build();
    }
}