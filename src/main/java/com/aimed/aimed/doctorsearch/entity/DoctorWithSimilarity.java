package com.aimed.aimed.doctorsearch.entity;

import java.time.LocalDate;

public record DoctorWithSimilarity(
        Long id,
        String address,
        String education,
        String description,
        LocalDate practiceStartDate,
        Float similarity
) { }
