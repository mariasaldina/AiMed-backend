package com.aimed.aimed.ollama;

import com.aimed.aimed.ollama.dto.*;
import com.aimed.aimed.ollama.enums.OllamaChatRole;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OllamaService {
    private final RestClient ollamaClient;
    private final OllamaProperties properties;

    public String generatePromptAnswer(String prompt, String model) {
        OllamaPromptRequestDto request = new OllamaPromptRequestDto(model, prompt, properties.defaultStream(), 10);
        try {
            OllamaPromptResponseDto response = ollamaClient.post()
                    .uri("/api/generate")
                    .body(request)
                    .retrieve()
                    .body(OllamaPromptResponseDto.class);
            return response != null ? response.getResponse() : "No response";
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Ollama error");
        }
    }

    public String generatePromptAnswer(String prompt) {
        return this.generatePromptAnswer(prompt, properties.defaultModel());
    }

    public OllamaChatMessage generateChatMessage(List<OllamaChatMessage> messages, String model) {
        OllamaChatRequestDto request = new OllamaChatRequestDto(
                model,
                messages,
                properties.defaultStream());

        try {
            OllamaChatResponseDto response = ollamaClient.post()
                    .uri("/api/chat")
                    .body(request)
                    .retrieve()
                    .body(OllamaChatResponseDto.class);
            return response != null
                    ? response.getMessage()
                    : new OllamaChatMessage(OllamaChatRole.assistant, "No message was produced");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "Ollama error");
        }
    }

    public OllamaChatMessage generateChatMessage(List<OllamaChatMessage> messages) {
        return this.generateChatMessage(messages, properties.defaultModel());
    }
}