package com.aimed.aimed.chat.dto;

import com.aimed.aimed.message.dto.MessageDto;

import java.util.List;

public record MessagePageDto(
        List<MessageDto> messages,
        Boolean hasMore
) { }
