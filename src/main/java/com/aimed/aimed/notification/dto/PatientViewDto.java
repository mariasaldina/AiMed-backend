package com.aimed.aimed.notification.dto;

import com.aimed.aimed.user.enums.Gender;

import java.time.LocalDate;

public record PatientViewDto(
        String address,
        LocalDate birthdate,
        Gender gender,
        String medicalHistory
) { }
