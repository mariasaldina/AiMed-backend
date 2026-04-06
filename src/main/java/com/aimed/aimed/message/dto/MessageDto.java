package com.aimed.aimed.message.dto;

import com.aimed.aimed.message.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Setter
@Getter
public class MessageDto {
    private Long id;
    private MessageType type;
    private OffsetDateTime createdAt;
    private UserMessageDto userPayload;
    private AssistantMessageDto assistantPayload;
    private DoctorSuggestionsMessageDto doctorSuggestionsPayload;
    private InvitationMessageDto invitationPayload;

    public MessageDto(Long id, MessageType type, OffsetDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.createdAt = createdAt;
    }
}
