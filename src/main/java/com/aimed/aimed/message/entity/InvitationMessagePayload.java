package com.aimed.aimed.message.entity;

import com.aimed.aimed.notification.dto.DoctorDataDto;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "invitation_message_payload")
public class InvitationMessagePayload {

    public InvitationMessagePayload(
            Message message,
            String content,
            DoctorDataDto doctorData
    ) {
        this.message = message;
        this.content = content;
        this.doctorData = doctorData;
    }

    @Id
    private Long messageId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    private String content;

    @Column(columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private DoctorDataDto doctorData;
}
