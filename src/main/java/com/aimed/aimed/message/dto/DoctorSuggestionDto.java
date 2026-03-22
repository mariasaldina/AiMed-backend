package com.aimed.aimed.message.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record DoctorSuggestionDto(
        Long userId,
        String fullName,
        List<String> specializations,
        String address,
        String education,
        String description,
        LocalDate practiceStartDate
) implements Serializable {}