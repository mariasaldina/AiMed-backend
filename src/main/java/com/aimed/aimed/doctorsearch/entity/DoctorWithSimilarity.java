package com.aimed.aimed.doctorsearch.entity;

import java.time.LocalDate;

public record DoctorWithSimilarity(
        Long userId,
        String address,
        String education,
        String description,
        LocalDate practiceStartDate,
        Float similarity
) { }
