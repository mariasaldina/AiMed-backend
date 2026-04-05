package com.aimed.aimed.auth.dto;

import com.aimed.aimed.user.enums.UserRole;

public record SignUpDto(
        String username,
        String password,
        String fullName,
        UserRole role
) {}
