package com.aimed.aimed.dev.dto;

import lombok.Builder;

import java.util.Set;

@Builder
public record DoctorGenerationRequestDto(
        Long id,
        String fullName,
        Set<String> specializations,
        Integer practiceYears
) {}