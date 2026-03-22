package com.aimed.aimed.user.dto;

import com.aimed.aimed.user.enums.ContactType;

import java.io.Serializable;

public record ContactResponseDto(
        Long id,
        ContactType type,
        String value,
        Boolean isPrimary
) implements Serializable {}