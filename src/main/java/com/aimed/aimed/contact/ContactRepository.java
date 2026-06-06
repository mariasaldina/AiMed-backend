package com.aimed.aimed.contact;

import com.aimed.aimed.contact.entity.Contact;
import com.aimed.aimed.user.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
    Optional<Contact> findByUserIdAndType(Long userId, ContactType type);
}
