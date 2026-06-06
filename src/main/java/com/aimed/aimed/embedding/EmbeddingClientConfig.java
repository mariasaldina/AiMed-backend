package com.aimed.aimed.embedding;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
@RequiredArgsConstructor
public class EmbeddingClientConfig {

    @Value("${embedding.url}")
    private String url;

    @Bean
    public RestClient embeddingClient() {
        return RestClient.builder().baseUrl(url).build();
    }
}
