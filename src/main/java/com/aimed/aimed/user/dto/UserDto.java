package com.aimed.aimed.user.dto;

import com.aimed.aimed.user.enums.UserRole;

public record UserDto(
        Long id,
        String username,
        String fullName,
        UserRole role,
        UserProfileDto profile
) {}