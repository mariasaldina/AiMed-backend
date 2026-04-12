package com.aimed.aimed.invitation.dto;

import com.aimed.aimed.invitation.enums.InvitationStatus;

import java.time.OffsetDateTime;

public record InvitationResponseDto(
        Long id,
        String content,
        OffsetDateTime createdAt,
        OffsetDateTime respondedAt,
        InvitationStatus status,
        DoctorViewDto doctor,
        PatientViewDto patient
) { }
