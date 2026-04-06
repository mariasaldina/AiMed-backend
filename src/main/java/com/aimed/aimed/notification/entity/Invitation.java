package com.aimed.aimed.notification.entity;

import com.aimed.aimed.message.entity.Message;
import com.aimed.aimed.notification.dto.DoctorDataDto;
import com.aimed.aimed.notification.enums.InvitationStatus;
import com.aimed.aimed.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Table(name = "invitations")
@Getter
@Setter
@NoArgsConstructor
public class Invitation {

    public Invitation(
            Long patientId,
            User doctor,
            String content,
            DoctorDataDto doctorData,
            Message message
    ) {
        this.patientId = patientId;
        this.doctor = doctor;
        this.content = content;
        this.createdAt = OffsetDateTime.now();
        this.status = InvitationStatus.PENDING;
        this.doctorData = doctorData;
        this.message = message;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private User doctor;

    private Long patientId;

    private String content;

    private OffsetDateTime createdAt;
    private OffsetDateTime respondedAt;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private InvitationStatus status;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private DoctorDataDto doctorData;
}
