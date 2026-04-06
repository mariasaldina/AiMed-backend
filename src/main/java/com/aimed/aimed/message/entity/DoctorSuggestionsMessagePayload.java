package com.aimed.aimed.message.entity;

import com.aimed.aimed.message.dto.DoctorSuggestionDto;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "doctor_suggestion_payload")
@Getter
@Setter
@NoArgsConstructor
public class DoctorSuggestionsMessagePayload {

    public DoctorSuggestionsMessagePayload(
            Message message,
            List<DoctorSuggestionDto> doctors
    ) {
        this.message = message;
        this.doctors = doctors;
    }

    @Id
    private Long messageId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "message_id")
    private Message message;

    @Column(columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private List<DoctorSuggestionDto> doctors;
}
