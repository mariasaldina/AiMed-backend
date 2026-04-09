package com.aimed.aimed.notification.mapper;

import com.aimed.aimed.notification.dto.PatientNotificationDto;
import com.aimed.aimed.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DoctorViewMapper.class})
public interface PatientNotificationMapper {

    @Mapping(target = "type", expression = "java(NotificationType.PATIENT)")
    @Mapping(target = "content", source = "notification.invitation.content")
    @Mapping(target = "invitationStatus", source = "notification.invitation.status")
    @Mapping(target = "doctor", source = "notification.invitation", qualifiedByName = "toDtoByStatus")
    PatientNotificationDto toDto(Notification notification);
}
