package com.aimed.aimed.notification.mapper;

import com.aimed.aimed.notification.dto.DoctorNotificationDto;
import com.aimed.aimed.notification.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PatientViewMapper.class})
public interface DoctorNotificationMapper {

    @Mapping(target = "type", expression = "java(NotificationType.DOCTOR)")
    @Mapping(target = "content", source = "notification.invitation.content")
    @Mapping(target = "invitationStatus", source = "notification.invitation.status")
    @Mapping(target = "patient", source = "notification.invitation.patient")
    DoctorNotificationDto toDto(Notification notification);
}