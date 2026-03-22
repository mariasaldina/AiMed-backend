package com.aimed.aimed.user.repository;

import com.aimed.aimed.user.entity.UserContact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<UserContact, Long> {

}
