package com.aimed.aimed.invitation.entity;

import com.aimed.aimed.invitation.enums.InvitationStatus;
import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Table(name = "invitations")
@Getter
@Setter
@NoArgsConstructor
public class Invitation {

    public Invitation(
            User patient,
            User doctor,
            String content
    ) {
        this.patient = patient;
        this.doctor = doctor;
        this.content = content;
        this.createdAt = OffsetDateTime.now();
        this.status = InvitationStatus.PENDING;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private User patient;

    private String content;

    private OffsetDateTime createdAt;
    private OffsetDateTime respondedAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private InvitationStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = true)
    private Message message;
}
