package com.aimed.aimed.message.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_message_payload")
@Getter
@Setter
@NoArgsConstructor
public class UserMessagePayload {

    public UserMessagePayload(Message message, String content) {
        this.message = message;
        this.content = content;
    }

    @Id
    private Long messageId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    private String content;
}
