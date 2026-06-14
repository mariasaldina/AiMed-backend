package com.aimed.aimed.ollama;

import com.aimed.aimed.ollama.dto.*;
import com.aimed.aimed.ollama.enums.OllamaChatRole;
import com.aimed.aimed.ollama.enums.Prompt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OllamaService {
    private final RestClient ollamaClient;
    private final OllamaProperties properties;

    private final String updateContextPrompt = this.readPrompt("update_context.txt");
    private final String assistantResponsePrompt = this.readPrompt("assistant_response.txt");
    private final String useContextPrompt = this.readPrompt("use_context.txt");
    private final String generateDoctorPrompt = this.readPrompt("generate_doctors.txt");
    private final String extractSpecializationsPrompt = this.readPrompt("extract_specializations.txt");

    public String readPrompt(String promptFileName) {
        try (InputStream is = getClass()
                .getClassLoader()
                .getResourceAsStream("prompts/" + promptFileName)) {
            if (is == null) {
                throw new IllegalStateException("Prompt not found");
            }

            return new String(is.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException(promptFileName + " loading failed");
        }
    }

    public String getPrompt(Prompt prompt) {
        return switch (prompt) {
            case ASSISTANT_RESPONSE -> assistantResponsePrompt;
            case GENERATE_DOCTORS -> generateDoctorPrompt;
            case UPDATE_CONTEXT -> updateContextPrompt;
            case USE_CONTEXT -> useContextPrompt;
            case EXTRACT_SPECIALIZATIONS -> extractSpecializationsPrompt;
        };
    }

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