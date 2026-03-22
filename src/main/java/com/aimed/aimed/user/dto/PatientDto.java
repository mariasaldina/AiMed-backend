package com.aimed.aimed.user.dto;

import com.aimed.aimed.user.enums.Gender;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record PatientDto(
        String address,

        @Past
        LocalDate birthdate,

        Gender gender,
        String medicalHistory
) {}
