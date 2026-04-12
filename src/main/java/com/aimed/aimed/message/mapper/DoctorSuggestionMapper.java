package com.aimed.aimed.message.mapper;

import com.aimed.aimed.message.dto.DoctorSuggestionDto;
import com.aimed.aimed.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorSuggestionMapper {

    @Mapping(target = "address", source = "user.doctorProfile.address")
    @Mapping(target = "education", source = "user.doctorProfile.education")
    @Mapping(target = "description", source = "user.doctorProfile.description")
    @Mapping(target = "practiceStartDate", source = "user.doctorProfile.practiceStartDate")
    @Mapping(target = "specializations", expression = "java(user.getDoctorProfile()" +
            ".getSpecializations().stream()" +
            ".map(com.aimed.aimed.specialization.Specialization::getName)" +
            ".toList())")
    @Mapping(target = "userId", source = "user.id")
    DoctorSuggestionDto toDto(User user);
}
