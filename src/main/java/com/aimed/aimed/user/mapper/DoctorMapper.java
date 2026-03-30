package com.aimed.aimed.user.mapper;

import com.aimed.aimed.user.dto.DoctorDto;
import com.aimed.aimed.user.entity.DoctorProfile;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    DoctorDto toDto(DoctorProfile doctorProfile);
}
