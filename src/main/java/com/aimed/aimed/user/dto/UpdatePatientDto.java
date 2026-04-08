package com.aimed.aimed.user.dto;

import com.aimed.aimed.user.enums.Gender;

import java.time.LocalDate;

public record UpdatePatientDto(
        String fullName,
        String address,
        LocalDate birthdate,
        Gender gender,
        String medicalHistory
) implements UserProfileDto {}