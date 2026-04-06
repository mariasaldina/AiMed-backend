package com.aimed.aimed.message.dto;

import com.aimed.aimed.notification.dto.DoctorDataDto;

public record InvitationMessageDto(
        DoctorDataDto doctorData,
        String content
) { }
