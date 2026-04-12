package com.aimed.aimed.notification.dto;

import com.aimed.aimed.invitation.enums.InvitationStatus;

import java.time.OffsetDateTime;

public record NotificationDto(
        Long id,
        InvitationStatus status,
        Long invitationId,
        Boolean isRead,
        OffsetDateTime createdAt
) { }