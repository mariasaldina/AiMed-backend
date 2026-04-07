package com.aimed.aimed.message.entity;

import com.aimed.aimed.message.dto.*;
import com.aimed.aimed.message.enums.MessageType;
import com.aimed.aimed.notification.entity.Invitation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    public Message(Long chatId, MessageType type) {
        this.chatId = chatId;
        this.type = type;
        this.createdAt = OffsetDateTime.now();
    }

    public MessageDto toDto() {
        MessageDto messageDto = new MessageDto(id, type, createdAt);
        switch (type) {
            case MessageType.USER -> {
                messageDto.setUserPayload(new UserMessageDto(userPayload.getContent()));
            }
            case MessageType.ASSISTANT -> {
                messageDto.setAssistantPayload(new AssistantMessageDto(
                        assistantPayload.getPossibleCauses(),
                        assistantPayload.getUrgency(),
                        assistantPayload.getRecommendations(),
                        assistantPayload.getDoctors()
                ));
            }
            case MessageType.DOCTOR_SUGGESTIONS -> {
                messageDto.setDoctorSuggestionsPayload(
                        new DoctorSuggestionsMessageDto(doctorSuggestionsPayload.getDoctors())
                );
            }
            case MessageType.INVITATION -> {
                messageDto.setInvitationPayload(new InvitationMessageDto(
                        invitationPayload.getDoctorData(),
                        invitationPayload.getContent()
                ));
            }
        }
        return messageDto;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @JsonIgnore
    private Long chatId;

    @NotNull
    private OffsetDateTime createdAt;

    @NotNull
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private MessageType type;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private UserMessagePayload userPayload;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private AssistantMessagePayload assistantPayload;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private DoctorSuggestionsMessagePayload doctorSuggestionsPayload;

    @OneToOne(mappedBy = "message", cascade = CascadeType.ALL, orphanRemoval = true)
    private InvitationMessagePayload invitationPayload;
}
