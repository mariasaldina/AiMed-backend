package com.aimed.aimed.contact;

import com.aimed.aimed.contact.entity.Contact;
import com.aimed.aimed.contact.mapper.ContactMapper;
import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.ContactType;
import com.aimed.aimed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContactService {

    private final ContactRepository contactRepository;
    private final UserRepository userRepository;
    private final ContactMapper contactMapper;

    public ContactsDto getContacts(Long userId) {
        return contactMapper.toDto(this.contactRepository.findAllByUserId(userId));
    }

    @Transactional
    public void updateContacts(Long userId, ContactsDto contactsDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "No such user"
                ));

        if (contactsDto.getEmail() != null) {
            saveOrUpdate(user, ContactType.EMAIL, contactsDto.getEmail(), contactsDto.getPrimaryContactType());
        }

        if (contactsDto.getPhone() != null) {
            saveOrUpdate(user, ContactType.PHONE, contactsDto.getPhone(), contactsDto.getPrimaryContactType());
        }

        if (contactsDto.getMessenger() != null) {
            saveOrUpdate(user, ContactType.MESSENGER, contactsDto.getMessenger(), contactsDto.getPrimaryContactType());
        }
    }

    private void saveOrUpdate(User user, ContactType type, String value, ContactType primaryType) {
        Optional<Contact> existing = contactRepository.findByUserIdAndType(user.getId(), type);

        if (existing.isPresent()) {
            existing.get().setValue(value);
            existing.get().setIsPrimary(type == primaryType);
            contactRepository.save(existing.get());
        } else {
            Contact contact = new Contact(user, type, value);
            contact.setIsPrimary(type == primaryType);
            contactRepository.save(contact);
        }
    }
}
