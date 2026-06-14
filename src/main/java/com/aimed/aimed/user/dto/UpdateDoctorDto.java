package com.aimed.aimed.user.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UpdateDoctorDto(
        String fullName,
        String address,
        String education,
        String description,
        LocalDate practiceStartDate,
        List<Long> specializationIds
) implements UserProfileDto { }
