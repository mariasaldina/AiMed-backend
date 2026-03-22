package com.aimed.aimed.ollama.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OllamaChatRequestDto {
    private String model;
    private List<OllamaChatMessage> messages;
    private boolean stream;
}
