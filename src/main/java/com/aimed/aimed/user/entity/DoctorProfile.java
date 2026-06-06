package com.aimed.aimed.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.aimed.aimed.specialization.Specialization;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "doctor_profile")
@Getter
@Setter
@NoArgsConstructor
public class DoctorProfile {

    public DoctorProfile(
            Long userId,
            String address,
            String education,
            String description,
            LocalDate practiceStartDate,
            String license,
            LocalDate licenseIssueDate,
            LocalDate licenseExpiryDate
    ) {
        this.userId = userId;
        this.address = address;
        this.education = education;
        this.description = description;
        this.practiceStartDate = practiceStartDate;
        this.license = license;
        this.licenseIssueDate = licenseIssueDate;
        this.licenseExpiryDate = licenseExpiryDate;
    }

    @Id
    private Long userId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private String address;
    private String education;
    private String description;
    private LocalDate practiceStartDate;
    private String license;
    private LocalDate licenseIssueDate;
    private LocalDate licenseExpiryDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "doctors_specializations",
            joinColumns = @JoinColumn(name = "doctor_id"),
            inverseJoinColumns = @JoinColumn(name = "specialization_id")
    )
    private Set<Specialization> specializations = new HashSet<>();

    @Column(columnDefinition = "vector(768)")
    private float[] profileEmbedding;
}
