package com.aimed.aimed.invitation;

import com.aimed.aimed.chat.ChatService;
import com.aimed.aimed.invitation.dto.InvitationRequestDto;
import com.aimed.aimed.invitation.dto.InvitationResponseDto;
import com.aimed.aimed.invitation.entity.Invitation;
import com.aimed.aimed.invitation.enums.InvitationStatus;
import com.aimed.aimed.invitation.mapper.InvitationMapper;
import com.aimed.aimed.invitation.repository.InvitationRepository;
import com.aimed.aimed.message.MessageService;
import com.aimed.aimed.message.dto.MessageDto;
import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.mapper.MessageMapper;
import com.aimed.aimed.notification.NotificationService;
import com.aimed.aimed.notification.dto.NotificationDto;
import com.aimed.aimed.notification.entity.Notification;
import com.aimed.aimed.user.UserService;
import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.UserRole;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationRepository invitationRepository;

    private final InvitationMapper invitationMapper;
    private final MessageMapper messageMapper;

    private final UserService userService;
    private final NotificationService notificationService;
    private final MessageService messageService;
    private final ChatService chatService;

    public Invitation getInvitation(Long invitationId) {
        return this.invitationRepository.findById(invitationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such invitation"));
    }

    @Transactional
    public MessageDto inviteDoctor(Long patientId, InvitationRequestDto invitationRequestDto) {
        Invitation invitation = this.invitationRepository.save(new Invitation(
                this.userService.getUser(patientId),
                this.userService.getUser(invitationRequestDto.doctorId()),
                invitationRequestDto.content()
        ));

        this.notificationService.save(
                invitationRequestDto.doctorId(),
                invitation,
                InvitationStatus.PENDING
        );

        Message invitationMessage = this.messageService.saveInvitationMessage(
                this.chatService.getChat(invitationRequestDto.chatId()),
                invitation
        );
        invitation.setMessage(invitationMessage);

        return messageMapper.toDto(invitationMessage);
    }

    @Transactional
    public void saveDoctorsResponse(Long doctorId, Long invitationId, InvitationStatus status) {
        Invitation invitation = getInvitation(invitationId);

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid invitation status");
        }

        invitation.setStatus(status);
        invitation.setRespondedAt(OffsetDateTime.now());
        invitationRepository.save(invitation);

        this.notificationService.save(
                invitation.getPatient().getId(),
                invitation,
                status
        );
    }

    public List<InvitationResponseDto> getAll(Long userId, UserRole role) {
        if (role == UserRole.PATIENT) {
            return this.invitationRepository.findAllByPatientIdOrderByCreatedAtDesc(userId).stream()
                    .map(invitationMapper::toDto)
                    .toList();
        }

        if (role == UserRole.DOCTOR) {
            return this.invitationRepository.findAllByDoctorIdAndStatusNotOrderByCreatedAtDesc(
                            userId,
                            InvitationStatus.CANCELLED
                    )
                    .stream()
                    .map(invitationMapper::toDto)
                    .toList();
        }

        return List.of();
    }

    @Transactional
    public void cancelInvitation(Long patientId, Long invitationId) {
        Invitation invitation = getInvitation(invitationId);
        if (!invitation.getPatient().getId().equals(patientId)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "You don't have rights to cancel this invitation"
            );
        }
        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Invalid invitation status");
        }

        invitation.setStatus(InvitationStatus.CANCELLED);
        invitationRepository.save(invitation);

        this.notificationService.save(
                invitation.getDoctor().getId(),
                invitation,
                InvitationStatus.CANCELLED
        );
    }
}