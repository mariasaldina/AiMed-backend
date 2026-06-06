package com.aimed.aimed.notification.repository;

import com.aimed.aimed.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverIdAndIsReadOrderByCreatedAtDesc(
            Long receiverId,
            Boolean isRead
    );
}
