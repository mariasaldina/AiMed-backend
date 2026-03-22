package com.aimed.aimed.message.dto;

import com.aimed.aimed.message.enums.UrgencyStatus;

import java.util.List;

public record AssistantMessageDto(
        List<String> possibleCauses,
        UrgencyStatus urgency,
        List<String> recommendations,
        List<String> doctors
) {}
