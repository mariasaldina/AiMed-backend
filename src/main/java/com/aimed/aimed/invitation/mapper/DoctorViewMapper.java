package com.aimed.aimed.invitation.mapper;

import com.aimed.aimed.contact.mapper.ContactMapper;
import com.aimed.aimed.invitation.dto.DoctorViewDto;
import com.aimed.aimed.invitation.entity.Invitation;
import com.aimed.aimed.invitation.enums.InvitationStatus;
import com.aimed.aimed.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

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
    DoctorViewDto toDtoNotApproved(User user);

    @Named("toDtoByStatus")
    default DoctorViewDto toDtoByStatus(Invitation invitation) {
        return invitation.getStatus() == InvitationStatus.APPROVED
                ? toDtoApproved(invitation.getDoctor())
                : toDtoNotApproved(invitation.getDoctor());
    }
}
