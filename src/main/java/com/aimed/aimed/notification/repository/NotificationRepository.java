package com.aimed.aimed.notification.repository;

import com.aimed.aimed.invitation.enums.InvitationStatus;
import com.aimed.aimed.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverIdAndIsReadOrderByCreatedAtDesc(
            Long receiverId,
            Boolean isRead
    );
}
