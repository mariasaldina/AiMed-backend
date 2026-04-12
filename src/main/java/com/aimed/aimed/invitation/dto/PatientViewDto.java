package com.aimed.aimed.invitation.dto;

import com.aimed.aimed.user.enums.Gender;

import java.time.LocalDate;

public record PatientViewDto(
        String fullName,
        String address,
        LocalDate birthdate,
        Gender gender,
        String medicalHistory
) { }
