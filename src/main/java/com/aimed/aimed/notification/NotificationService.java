package com.aimed.aimed.notification;

import com.aimed.aimed.chat.ChatRepository;
import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.message.MessageRepository;
import com.aimed.aimed.message.dto.MessageDto;
import com.aimed.aimed.message.entity.InvitationMessagePayload;
import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.enums.MessageType;
import com.aimed.aimed.notification.dto.*;
import com.aimed.aimed.notification.entity.Invitation;
import com.aimed.aimed.notification.entity.Notification;
import com.aimed.aimed.notification.enums.InvitationStatus;
import com.aimed.aimed.notification.enums.NotificationType;
import com.aimed.aimed.notification.mapper.DoctorNotificationMapper;
import com.aimed.aimed.notification.mapper.DoctorViewMapper;
import com.aimed.aimed.notification.mapper.PatientNotificationMapper;
import com.aimed.aimed.notification.mapper.PatientViewMapper;
import com.aimed.aimed.notification.repository.InvitationRepository;
import com.aimed.aimed.notification.repository.NotificationRepository;
import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.UserRole;
import com.aimed.aimed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final InvitationRepository invitationRepository;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final ChatRepository chatRepository;

    private final PatientNotificationMapper patientNotificationMapper;
    private final DoctorNotificationMapper doctorNotificationMapper;

    @Transactional
    public MessageDto inviteDoctor(Long patientId, InvitationDto invitationDto) {
        User doctor = this.userRepository.findById(invitationDto.doctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such doctor"));
        Chat chat = this.chatRepository.findById(invitationDto.chatId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such chat"));

        Invitation invitation = this.invitationRepository.save(new Invitation(
                this.userRepository.getReferenceById(patientId),
                doctor,
                invitationDto.content()
        ));

        this.notificationRepository.save(
                new Notification(
                        invitationDto.doctorId(),
                        NotificationType.DOCTOR,
                        invitation
                )
        );

        Message invitationMessage = new Message(chat, MessageType.INVITATION);
        invitationMessage.setInvitationPayload(
                new InvitationMessagePayload(
                        invitationMessage,
                        invitationDto.content(),
                        new DoctorDataDto(doctor.getFullName())
                )
        );
        Message savedInvitationMessage = this.messageRepository.save(invitationMessage);

        return savedInvitationMessage.toDto();
    }

    @Transactional
    public void notifyPatient(Long doctorId, Long notificationId, InvitationStatus status) {
        Notification notification = this.notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such notification"));
        Invitation invitation = notification.getInvitation();

        invitation.setStatus(status);
        invitation.setRespondedAt(OffsetDateTime.now());
        invitationRepository.save(invitation);

        this.notificationRepository.save(
                new Notification(invitation.getPatient().getId(), NotificationType.PATIENT, invitation)
        );
    }

    private List<NotificationDto> mapNotificationList(List<Notification> notifications, UserRole role) {
        switch (role) {
            case UserRole.PATIENT -> {
                return notifications.stream()
                        .map(patientNotificationMapper::toDto)
                        .map(n -> (NotificationDto) n)
                        .toList();
            }
            case UserRole.DOCTOR -> {
                return notifications.stream()
                        .map(doctorNotificationMapper::toDto)
                        .map(n -> (NotificationDto) n)
                        .toList();
            }
            default -> { return List.of(); }
        }
    }

    public NotificationListDto getNotifications(Long userId, UserRole role) {
        return new NotificationListDto(
                mapNotificationList(this.notificationRepository
                        .findByReceiverIdAndIsReadOrderByCreatedAtDesc(userId, true), role),
                mapNotificationList(this.notificationRepository
                        .findByReceiverIdAndIsReadOrderByCreatedAtDesc(userId, false), role)
        );
    }

    @Transactional
    public void readNotifications(List<Long> notificationIds) {
        this.notificationRepository.findAllById(notificationIds)
                .forEach(n -> n.setIsRead(true));
    }
}
