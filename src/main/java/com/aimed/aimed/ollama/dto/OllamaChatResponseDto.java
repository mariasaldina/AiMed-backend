package com.aimed.aimed.ollama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OllamaChatResponseDto {
    private String model;
    private OllamaChatMessage message;
}
