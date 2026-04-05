package com.aimed.aimed.user.dto;

import com.aimed.aimed.contact.ContactsDto;
import com.aimed.aimed.user.enums.UserRole;

public record UserResponseDto(
        Long id,
        String username,
        String fullName,
        UserRole role,
        UserProfileDto profile,
        ContactsDto contacts
) {}