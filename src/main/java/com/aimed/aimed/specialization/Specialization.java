package com.aimed.aimed.specialization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.aimed.aimed.user.entity.DoctorProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "specializations")
@Getter
@Setter
@NoArgsConstructor
public class Specialization {

    public Specialization(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @ManyToMany(mappedBy = "specializations")
    @JsonIgnore
    private Set<DoctorProfile> doctors;
}
