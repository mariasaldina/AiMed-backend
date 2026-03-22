package com.aimed.aimed.ollama;

import com.aimed.aimed.ollama.dto.*;
import com.aimed.aimed.ollama.enums.OllamaChatRole;
import com.symptomassistant.symptom_assistant.ollama.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Service
public class OllamaService {
    private final WebClient webClient;
    private final OllamaProperties properties;

    public OllamaService(WebClient webClient, OllamaProperties properties) {
        this.webClient = webClient;
        this.properties = properties;
    }

    public String generatePromptAnswer(String prompt, String model) {
        OllamaPromptRequestDto request = new OllamaPromptRequestDto(model, prompt, properties.defaultStream(), 10);
        try {
            OllamaPromptResponseDto response = webClient.post()
                    .uri("/api/generate")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaPromptResponseDto.class)
                    .block(Duration.ofSeconds(60));
            return response != null ? response.getResponse() : "No response";
        } catch (Exception e) {
            return e.getMessage();
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
            OllamaChatResponseDto response = webClient.post()
                    .uri("/api/chat")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(OllamaChatResponseDto.class)
                    .block(Duration.ofSeconds(60));
            return response != null
                    ? response.getMessage()
                    : new OllamaChatMessage(OllamaChatRole.assistant, "No message was produced");
        } catch (Exception e) {
            return new OllamaChatMessage(OllamaChatRole.assistant, e.getMessage());
        }
    }

    public OllamaChatMessage generateChatMessage(List<OllamaChatMessage> messages) {
        return this.generateChatMessage(messages, properties.defaultModel());
    }
}