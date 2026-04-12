package com.aimed.aimed.notification;

import com.aimed.aimed.notification.dto.*;
import com.aimed.aimed.invitation.entity.Invitation;
import com.aimed.aimed.notification.entity.Notification;
import com.aimed.aimed.invitation.enums.InvitationStatus;
import com.aimed.aimed.notification.mapper.NotificationMapper;
import com.aimed.aimed.notification.repository.NotificationRepository;
import com.aimed.aimed.user.enums.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final NotificationMapper notificationMapper;

    public Notification getNotification(Long notificationId) {
        return this.notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such notification"));
    }

    public Notification save(Long doctorId, Invitation invitation, InvitationStatus historicStatus) {
        return this.notificationRepository.save(new Notification(doctorId, historicStatus, invitation));
    }

    private List<NotificationDto> getAll(Long userId, Boolean isRead) {
        return this.notificationRepository
                .findByReceiverIdAndIsReadOrderByCreatedAtDesc(userId, isRead)
                .stream()
                .map(notificationMapper::toDto)
                .toList();
    }

    public NotificationListDto getNotifications(Long userId, UserRole role) {
        return new NotificationListDto(getAll(userId, true), getAll(userId, false));
    }

    @Transactional
    public void readNotifications(List<Long> notificationIds) {
        this.notificationRepository.findAllById(notificationIds)
                .forEach(n -> n.setIsRead(true));
    }
}
