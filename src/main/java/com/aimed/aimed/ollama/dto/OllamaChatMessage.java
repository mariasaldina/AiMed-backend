package com.aimed.aimed.ollama.dto;

import com.aimed.aimed.ollama.enums.OllamaChatRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OllamaChatMessage {
    private OllamaChatRole role;
    private String content;
}
