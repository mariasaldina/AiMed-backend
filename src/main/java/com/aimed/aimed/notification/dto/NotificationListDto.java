package com.aimed.aimed.notification.dto;

import java.util.List;

public record NotificationListDto(
        List<NotificationDto> read,
        List<NotificationDto> unread
) { }
