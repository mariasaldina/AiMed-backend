package com.aimed.aimed.invitation.repository;

import com.aimed.aimed.invitation.entity.Invitation;
import com.aimed.aimed.invitation.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findAllByPatientIdOrderByCreatedAtDesc(Long patientId);
    List<Invitation> findAllByDoctorIdAndStatusNotOrderByCreatedAtDesc(Long doctorId, InvitationStatus status);
}
