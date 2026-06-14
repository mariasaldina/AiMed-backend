package com.aimed.aimed.chat.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Array;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Table(name = "chats")
@Getter
@Setter
@NoArgsConstructor
public class Chat {

    public Chat(Long userId, String title) {
        this.userId = userId;
        this.title = title;
        this.createdAt = OffsetDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private String title;

    private String context;

    private OffsetDateTime lastMessageAt;
    private OffsetDateTime createdAt;
    private OffsetDateTime lastUserMessageAt;
    private OffsetDateTime lastDoctorSearchAt;

    @JdbcTypeCode(SqlTypes.VECTOR)
    @Array(length = 768)
    private float[] contextEmbedding;
}
