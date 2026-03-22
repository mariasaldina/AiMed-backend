package com.aimed.aimed.user.repository;

import com.aimed.aimed.user.entity.DoctorProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    @Query("SELECT d FROM DoctorProfile d JOIN d.specializations s WHERE s.id IN :specializationIds")
    List<DoctorProfile> findAllBySpecializationIds(@Param("specializationIds") List<Long> specializationIds);
}
