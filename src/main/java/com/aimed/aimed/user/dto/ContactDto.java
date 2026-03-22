package com.aimed.aimed.user.dto;

import com.aimed.aimed.user.enums.ContactType;

public record ContactDto(
        ContactType type,
        String value,
        Boolean isPrimary
) {}