package com.aimed.aimed.message.mapper;

import com.aimed.aimed.invitation.entity.Invitation;
import com.aimed.aimed.message.dto.*;
import com.aimed.aimed.message.entity.AssistantMessagePayload;
import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.message.entity.UserMessagePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DoctorSuggestionMapper.class})
public interface MessageMapper {

    MessageDto toDto(Message message);

    UserMessageDto toDto(UserMessagePayload payload);
    AssistantMessageDto toDto(AssistantMessagePayload payload);

    @Mapping(target = "fullName", source = "invitation.doctor.fullName")
    InvitationMessageDto toDto(Invitation invitation);

    AssistantMessagePayload toPayload(AssistantMessageDto dto, Message message);
}
