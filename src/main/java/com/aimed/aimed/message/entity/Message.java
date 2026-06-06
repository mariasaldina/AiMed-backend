package com.aimed.aimed.message.entity;

import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.invitation.entity.Invitation;
import com.aimed.aimed.message.enums.MessageType;
import com.aimed.aimed.user.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "messages")
@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {

    public Message(Chat chat, MessageType type) {
        this.chat = chat;
        this.type = type;
        this.createdAt = OffsetDateTime.now();
        this.chat.setLastMessageAt(this.createdAt);
        if (type == MessageType.USER) {
            this.chat.setLastUserMessageAt(this.createdAt);
        }
        if (type == MessageType.DOCTOR_SUGGESTIONS) {
            this.chat.setLastDoctorSearchAt(this.createdAt);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id")
    private Chat chat;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctor_suggestions",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "doctor_id")
    )
    private List<User> doctorSuggestionsPayload;

    @OneToOne(mappedBy = "message")
    private Invitation invitationPayload;
}
