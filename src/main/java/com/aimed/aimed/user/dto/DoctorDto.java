package com.aimed.aimed.user.dto;

import java.time.LocalDate;
import java.util.List;

public record DoctorDto(
        String address,
        String education,
        String description,
        LocalDate practiceStartDate,
        String license,
        LocalDate licenseIssueDate,
        LocalDate licenseExpiryDate,
        List<Long> specializationIds
) {}
