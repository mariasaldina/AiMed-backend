package com.aimed.aimed.message.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.aimed.aimed.user.dto.ContactResponseDto;
import io.hypersistence.utils.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.List;

@Entity
@Table(name = "contacts_message_payload")
@Getter
@Setter
@NoArgsConstructor
public class ContactsMessagePayload {

    public ContactsMessagePayload(
            Message message,
            Long doctorId,
            List<ContactResponseDto> content
    ) {
        this.message = message;
        this.doctorId = doctorId;
        this.content = content;
    }

    @Id
    @JsonIgnore
    private Long messageId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "message_id")
    @JsonIgnore
    private Message message;

    @NotNull
    private Long doctorId;

    @Column(columnDefinition = "jsonb")
    @Type(JsonBinaryType.class)
    private List<ContactResponseDto> content;
}
