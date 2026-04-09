package com.aimed.aimed.notification;

import com.aimed.aimed.message.dto.MessageDto;
import com.aimed.aimed.notification.dto.InvitationDto;
import com.aimed.aimed.notification.dto.NotificationListDto;
import com.aimed.aimed.notification.enums.InvitationStatus;
import com.aimed.aimed.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/invite")
    public MessageDto inviteDoctor(@AuthenticationPrincipal Jwt jwt, @RequestBody InvitationDto dto) {
        Long patientId = Long.valueOf(jwt.getSubject());
        return this.notificationService.inviteDoctor(patientId, dto);
    }

    public record NotifyPatientDto(
            InvitationStatus status
    ) {}

    @PostMapping("/{notificationId}/answer")
    public void notifyPatient(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("notificationId") Long notificationId,
            @RequestBody NotifyPatientDto dto
    ) {
        Long doctorId = Long.valueOf(jwt.getSubject());
        this.notificationService.notifyPatient(doctorId, notificationId, dto.status);
    }

    @GetMapping("")
    public NotificationListDto getNotifications(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        UserRole role = UserRole.valueOf(jwt.getClaimAsString("role").replace("ROLE_", ""));
        return this.notificationService.getNotifications(userId, role);
    }

    public record NotificationsToReadDto (
            List<Long> notificationIds
    ) {}

    @PatchMapping("/read")
    public void readNotification(@RequestBody NotificationsToReadDto dto) {
        this.notificationService.readNotifications(dto.notificationIds());
    }
}
