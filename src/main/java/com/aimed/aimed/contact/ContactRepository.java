package com.aimed.aimed.contact;

import com.aimed.aimed.contact.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
}
