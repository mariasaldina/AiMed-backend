package com.aimed.aimed.chat.dto;

import java.time.OffsetDateTime;

public record ChatDto (
        Long id,
        String title,
        OffsetDateTime lastMessageAt
) {}