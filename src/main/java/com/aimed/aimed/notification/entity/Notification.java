package com.aimed.aimed.notification.entity;

import com.aimed.aimed.notification.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "notifications")
public class Notification {

    public Notification(
            Long receiverId,
            NotificationType type,
            Invitation invitation
    ) {
        this.receiverId = receiverId;
        this.type = type;
        this.invitation = invitation;
        this.isRead = false;
        this.createdAt = OffsetDateTime.now();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private NotificationType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invitation_id")
    private Invitation invitation;

    private Boolean isRead;
    private OffsetDateTime createdAt;
}
