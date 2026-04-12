package com.aimed.aimed.invitation.mapper;

import com.aimed.aimed.invitation.dto.PatientViewDto;
import com.aimed.aimed.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientViewMapper {

    @Mapping(target = "fullName", source = "user.fullName")
    @Mapping(target = "address", source = "user.patientProfile.address")
    @Mapping(target = "birthdate", source = "user.patientProfile.birthdate")
    @Mapping(target = "gender", source = "user.patientProfile.gender")
    @Mapping(target = "medicalHistory", source = "user.patientProfile.medicalHistory")
    PatientViewDto toPatientViewDto(User user);
}
