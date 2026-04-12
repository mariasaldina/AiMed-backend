package com.aimed.aimed.invitation.mapper;

import com.aimed.aimed.invitation.dto.InvitationResponseDto;
import com.aimed.aimed.invitation.entity.Invitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DoctorViewMapper.class, PatientViewMapper.class})
public interface InvitationMapper {

    @Mapping(target = "doctor", source = "invitation", qualifiedByName = "toDtoByStatus")
    InvitationResponseDto toDto(Invitation invitation);
}