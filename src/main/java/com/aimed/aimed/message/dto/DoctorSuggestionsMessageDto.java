package com.aimed.aimed.message.dto;

import java.util.List;

public record DoctorSuggestionsMessageDto(
        List<DoctorSuggestionDto> doctors
) {}