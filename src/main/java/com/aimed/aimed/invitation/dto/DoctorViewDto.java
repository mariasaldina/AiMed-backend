package com.aimed.aimed.invitation.dto;

import com.aimed.aimed.contact.ContactsDto;

import java.time.LocalDate;
import java.util.List;

public record DoctorViewDto(
        String username,
        String fullName,
        String address,
        String education,
        String description,
        LocalDate practiceStartDate,
        List<String> specializations,
        ContactsDto contacts
) { }
