package com.aimed.aimed.notification.dto;

import com.aimed.aimed.contact.ContactsDto;

import java.time.LocalDate;
import java.util.List;

public record DoctorViewDto(
        String fullName,
        String address,
        String education,
        String description,
        LocalDate practiceStartDate,
        List<String> specializations,
        ContactsDto contacts
) { }
