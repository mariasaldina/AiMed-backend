package com.aimed.aimed.user.entity;

import com.aimed.aimed.user.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;

@Entity
@Table(name = "patient_profile")
@Getter
@Setter
@NoArgsConstructor
public class PatientProfile {

    public PatientProfile(
            Long userId,
            String address,
            LocalDate birthdate,
            Gender gender,
            String medicalHistory
    ) {
        this.userId = userId;
        this.address = address;
        this.birthdate = birthdate;
        this.gender = gender;
        this.medicalHistory = medicalHistory;
    }

    @Id
    private Long userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String address;
    private LocalDate birthdate;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private Gender gender;

    private String medicalHistory;
}
