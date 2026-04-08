package com.aimed.aimed.notification.mapper;

import com.aimed.aimed.contact.mapper.ContactMapper;
import com.aimed.aimed.notification.dto.DoctorViewDto;
import com.aimed.aimed.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContactMapper.class})
public interface DoctorViewMapper {

    @Mapping(target = "address", source = "user.doctorProfile.address")
    @Mapping(target = "education", source = "user.doctorProfile.education")
    @Mapping(target = "description", source = "user.doctorProfile.description")
    @Mapping(target = "practiceStartDate", source = "user.doctorProfile.practiceStartDate")
    @Mapping(target = "specializations", expression = "java(user.getDoctorProfile()" +
            ".getSpecializations().stream()" +
            ".map(com.aimed.aimed.specialization.Specialization::getName)" +
            ".toList())")
    @Mapping(target = "contacts", source = "user.contacts")
    DoctorViewDto toDtoApproved(User user);

    @Mapping(target = "address", source = "user.doctorProfile.address")
    @Mapping(target = "education", source = "user.doctorProfile.education")
    @Mapping(target = "description", source = "user.doctorProfile.description")
    @Mapping(target = "practiceStartDate", source = "user.doctorProfile.practiceStartDate")
    @Mapping(target = "specializations", expression = "java(user.getDoctorProfile()" +
            ".getSpecializations().stream()" +
            ".map(com.aimed.aimed.specialization.Specialization::getName)" +
            ".toList())")
    @Mapping(target = "contacts", expression = "java(null)")
    DoctorViewDto toDtoRejected(User user);
}
