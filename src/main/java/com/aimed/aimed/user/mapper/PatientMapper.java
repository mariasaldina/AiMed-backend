package com.aimed.aimed.user.mapper;

import com.aimed.aimed.user.dto.PatientDto;
import com.aimed.aimed.user.entity.PatientProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    PatientDto toDto(PatientProfile patientProfile);
}
