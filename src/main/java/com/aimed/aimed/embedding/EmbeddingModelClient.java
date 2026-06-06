package com.aimed.aimed.embedding;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmbeddingModelClient {

    private final RestClient embeddingClient;

    @Retry(name = "embeddingRetry")
    public float[] getEmbedding(String text) {
        try {
            float[][] res = embeddingClient.post()
                    .uri("/embed")
                    .body(Map.of(
                            "inputs", text,
                            "normalize", true,
                            "dimensions", 768,
                            "truncate", true
                    ))
                    .retrieve()
                    .body(float[][].class);
            if (res == null || res.length == 0) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No embedding produced");
            }
            return res[0];
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "Embedding service is unavailable");
        }
    }
}
