package com.aimed.aimed.notification.mapper;

import com.aimed.aimed.notification.dto.NotificationDto;
import com.aimed.aimed.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "invitationId", source = "notification.invitation.id")
    @Mapping(target = "status", source = "notification.historicStatus")
    NotificationDto toDto(Notification notification);
}
