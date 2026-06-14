package com.aimed.aimed.doctorsearch.entity;

import lombok.Builder;

@Builder
public record SearchCriteria(
        Boolean filterBlankEducation,
        Boolean filterBlankDescription,
        Boolean orderByExperience
) { }
