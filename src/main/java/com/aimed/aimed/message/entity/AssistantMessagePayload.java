package com.aimed.aimed.message.entity;

import com.aimed.aimed.message.enums.UrgencyStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "assistant_message_payload")
@Getter
@Setter
@NoArgsConstructor
public class AssistantMessagePayload {

    public AssistantMessagePayload(
            Message message,
            List<String> possibleCauses,
            List<String> recommendations,
            UrgencyStatus urgency,
            List<String> doctors
    ) {
        this.message = message;
        this.possibleCauses = possibleCauses;
        this.recommendations = recommendations;
        this.urgency = urgency;
        this.doctors = doctors;
    }

    @Id
    private Long messageId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    private List<String> possibleCauses;

    private List<String> recommendations;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(columnDefinition = "urgency")
    private UrgencyStatus urgency;

    private List<String> doctors;
}
