package com.aimed.aimed.invitation.dto;

public record InvitationRequestDto(
        Long chatId,
        Long doctorId,
        String content
) { }
