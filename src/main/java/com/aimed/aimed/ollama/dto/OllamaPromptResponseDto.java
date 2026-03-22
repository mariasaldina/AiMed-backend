package com.aimed.aimed.ollama.dto;

import lombok.Data;

@Data
public class OllamaPromptResponseDto {
    private String model;
    private String response;
    private boolean done;
}
