package com.aimed.aimed.auth.dto;

import com.aimed.aimed.user.enums.UserRole;

public record SignUpResponseDto (
        Long id,
        String username,
        UserRole role
) {}