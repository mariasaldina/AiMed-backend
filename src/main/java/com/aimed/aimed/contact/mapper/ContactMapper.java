package com.aimed.aimed.contact.mapper;

import com.aimed.aimed.contact.ContactsDto;
import com.aimed.aimed.contact.entity.Contact;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ContactMapper {

    default ContactsDto toDto(List<Contact> contacts) {
        if (contacts == null) {
            return null;
        }

        ContactsDto dto = new ContactsDto();

        for (Contact c : contacts) {
            switch (c.getType()) {
                case EMAIL -> dto.setEmail(c.getValue());
                case PHONE -> dto.setPhone(c.getValue());
                case MESSENGER -> dto.setMessenger(c.getValue());
            }

            if (c.getIsPrimary()) {
                dto.setPrimaryContactType(c.getType());
            }
        }

        return dto;
    }
}