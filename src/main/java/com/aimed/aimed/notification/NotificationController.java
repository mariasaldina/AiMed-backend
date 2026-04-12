package com.aimed.aimed.notification;

import com.aimed.aimed.notification.dto.NotificationListDto;
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
