package com.aimed.aimed.notification.repository;

import com.aimed.aimed.notification.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {
}
