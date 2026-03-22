package com.aimed.aimed.chat.dto;

import com.aimed.aimed.message.entity.Message;

public record MessageResponseDto (
        Message userMessage,
        Message assistantMessage
) {}