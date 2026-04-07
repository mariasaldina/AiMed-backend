package com.aimed.aimed.notification;

import com.aimed.aimed.message.MessageRepository;
import com.aimed.aimed.message.dto.MessageDto;
import com.aimed.aimed.message.entity.InvitationMessagePayload;
import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.enums.MessageType;
import com.aimed.aimed.notification.dto.DoctorDataDto;
import com.aimed.aimed.notification.dto.DoctorNotificationDto;
import com.aimed.aimed.notification.dto.InvitationDto;
import com.aimed.aimed.notification.dto.PatientNotificationDto;
import com.aimed.aimed.notification.entity.Invitation;
import com.aimed.aimed.notification.entity.Notification;
import com.aimed.aimed.notification.enums.InvitationStatus;
import com.aimed.aimed.notification.enums.NotificationType;
import com.aimed.aimed.notification.mapper.DoctorViewMapper;
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

    private final PatientViewMapper patientViewMapper;
    private final DoctorViewMapper doctorViewMapper;

    @Transactional
    public MessageDto inviteDoctor(Long patientId, InvitationDto invitationDto) {
        User doctor = this.userRepository.findById(invitationDto.doctorId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such doctor"));

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

        Message invitationMessage = new Message(invitationDto.chatId(), MessageType.INVITATION);
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

    public List<?> getNotifications(Long userId, UserRole role) {
        List<Notification> notifications = this.notificationRepository.findByReceiverId(userId);

        switch (role) {
            case UserRole.PATIENT -> {
                return notifications.stream()
                        .map(n -> {
                            InvitationStatus status = n.getInvitation().getStatus();
                            return new PatientNotificationDto(
                                    n.getId(),
                                    NotificationType.PATIENT,
                                    n.getCreatedAt(),
                                    n.getInvitation().getContent(),
                                    status,
                                    status == InvitationStatus.APPROVED
                                            ? doctorViewMapper.toDoctorViewDto(n.getInvitation().getDoctor())
                                            : null
                            );
                        })
                        .toList();
            }
            case UserRole.DOCTOR -> {
                return notifications.stream()
                        .map(n -> {
                            InvitationStatus status = n.getInvitation().getStatus();
                            return new DoctorNotificationDto(
                                    n.getId(),
                                    NotificationType.DOCTOR,
                                    n.getCreatedAt(),
                                    n.getInvitation().getContent(),
                                    n.getInvitation().getStatus(),
                                    patientViewMapper.toPatientViewDto(n.getInvitation().getPatient())
                            );
                        })
                        .toList();
            }
            default -> { return List.of(); }
        }
    }
}
