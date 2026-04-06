package com.aimed.aimed.notification.dto;

public record InvitationDto(
        Long chatId,
        Long doctorId,
        String content
) { }
