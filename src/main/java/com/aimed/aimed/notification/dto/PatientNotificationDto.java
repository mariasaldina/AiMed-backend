package com.aimed.aimed.notification.dto;

import com.aimed.aimed.notification.enums.InvitationStatus;
import com.aimed.aimed.notification.enums.NotificationType;

import java.time.OffsetDateTime;

public record PatientNotificationDto(
        Long id,
        NotificationType type,
        OffsetDateTime createdAt,
        String content,
        InvitationStatus invitationStatus,
        DoctorViewDto doctor
) { }
