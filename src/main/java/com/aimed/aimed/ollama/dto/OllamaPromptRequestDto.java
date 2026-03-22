package com.aimed.aimed.ollama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OllamaPromptRequestDto {
    private String model;
    private String prompt;
    private boolean stream;
    private Integer maxTokens;
}
