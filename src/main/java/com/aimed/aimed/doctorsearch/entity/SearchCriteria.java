package com.aimed.aimed.doctorsearch.entity;

import lombok.Builder;

@Builder
public record SearchCriteria(
        Boolean filterLicense,
        Boolean filterBlankEducation,
        Boolean filterBlankDescription,
        Boolean orderByExperience
) { }
