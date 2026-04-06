package com.aimed.aimed.chat.dto;

import com.aimed.aimed.message.dto.MessageDto;
import com.aimed.aimed.message.entity.Message;

public record MessageResponseDto (
        MessageDto userMessage,
        MessageDto assistantMessage
) {}