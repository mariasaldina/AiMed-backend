package com.aimed.aimed.contact;

import com.aimed.aimed.contact.entity.Contact;
import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.ContactType;
import com.aimed.aimed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public ContactsDto parseToDto(List<Contact> contacts) {
        ContactsDto contactDto = new ContactsDto();

        contacts
                .forEach(c -> {
                    switch (c.getType()) {
                        case ContactType.EMAIL -> contactDto.setEmail(c.getValue());
                        case ContactType.PHONE -> contactDto.setPhone(c.getValue());
                        case ContactType.MESSENGER -> contactDto.setMessenger(c.getValue());
                    }
                    if (c.getIsPrimary()) {
                        contactDto.setPrimaryContactType(c.getType());
                    }
                });

        return contactDto;
    }

    public ContactsDto getContacts(Long userId) {
        return parseToDto(this.contactRepository.findAllByUserId(userId));
    }

    @Transactional
    public void updateContacts(Long userId, ContactsDto contactsDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No such user"
                ));

        contactRepository.deleteAllByUserId(userId);

        if (contactsDto.getEmail() != null) {
            saveContact(user, ContactType.EMAIL,
                    contactsDto.getEmail(),
                    contactsDto.getPrimaryContactType());
        }

        if (contactsDto.getPhone() != null) {
            saveContact(user, ContactType.PHONE,
                    contactsDto.getPhone(),
                    contactsDto.getPrimaryContactType());
        }

        if (contactsDto.getMessenger() != null) {
            saveContact(user, ContactType.MESSENGER,
                    contactsDto.getMessenger(),
                    contactsDto.getPrimaryContactType());
        }
    }

    private void saveContact(
            User user,
            ContactType type,
            String value,
            ContactType primaryType
    ) {
        Contact contact = new Contact(user, type, value);
        contact.setIsPrimary(type == primaryType);
        contactRepository.save(contact);
    }
}
