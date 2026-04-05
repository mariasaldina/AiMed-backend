package com.aimed.aimed.user.mapper;

import com.aimed.aimed.user.dto.DoctorDto;
import com.aimed.aimed.user.entity.DoctorProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "specializationIds", expression = "java(doctorProfile" +
            ".getSpecializations().stream()" +
            ".map(com.aimed.aimed.specialization.Specialization::getId)" +
            ".toList())")
    DoctorDto toDto(DoctorProfile doctorProfile);
}
