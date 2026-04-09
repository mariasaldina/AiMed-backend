package com.aimed.aimed.user;

import com.aimed.aimed.contact.ContactsDto;
import com.aimed.aimed.contact.mapper.ContactMapper;
import com.aimed.aimed.specialization.Specialization;
import com.aimed.aimed.specialization.SpecializationRepository;
import com.aimed.aimed.user.dto.*;
import com.aimed.aimed.user.entity.*;
import com.aimed.aimed.user.enums.UserRole;
import com.aimed.aimed.user.mapper.DoctorMapper;
import com.aimed.aimed.user.mapper.PatientMapper;
import com.aimed.aimed.user.repository.DoctorProfileRepository;
import com.aimed.aimed.user.repository.PatientProfileRepository;
import com.aimed.aimed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PatientProfileRepository patientRepository;
    private final DoctorProfileRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final ContactMapper contactMapper;

    @Transactional
    public User create(String username, String password, UserRole role) {
        User user = new User(username, password, role);
        if (role == UserRole.PATIENT) {
            PatientProfile patientProfile = new PatientProfile();
            patientProfile.setUser(user);
            user.setPatientProfile(patientProfile);
        } else {
            DoctorProfile doctorProfile = new DoctorProfile();
            doctorProfile.setUser(user);
            user.setDoctorProfile(doctorProfile);
        }
        return this.userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    public UserResponseDto findById(Long id) {
        User user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        UserProfileDto profile =
                switch (user.getRole()) {
                    case PATIENT -> patientMapper.toDto(user.getPatientProfile());
                    case DOCTOR -> doctorMapper.toDto(user.getDoctorProfile());
                    default -> null;
                };

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                profile,
                contactMapper.toDto(user.getContacts())
        );
    }

    @Transactional
    public UserResponseDto fillInPatientQuestionnaire(Long userId, UpdatePatientDto patientDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
        if (user.getRole() != UserRole.PATIENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not a patient");
        }

        PatientProfile patientProfile = patientRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        user.setFullName(patientDto.fullName());
        patientProfile.setAddress(patientDto.address());
        patientProfile.setBirthdate(patientDto.birthdate());
        patientProfile.setGender(patientDto.gender());
        patientProfile.setMedicalHistory(patientDto.medicalHistory());

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                patientMapper.toDto(user.getPatientProfile()),
                contactMapper.toDto(user.getContacts())
        );
    }

    @Transactional
    public UserResponseDto fillInDoctorQuestionnaire(Long userId, UpdateDoctorDto doctorDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
        if (user.getRole() != UserRole.DOCTOR) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not a doctor");
        }

        DoctorProfile doctorProfile = doctorRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        user.setFullName(doctorDto.fullName());
        doctorProfile.setAddress(doctorDto.address());
        doctorProfile.setEducation(doctorDto.education());
        doctorProfile.setDescription(doctorDto.description());
        doctorProfile.setPracticeStartDate(doctorDto.practiceStartDate());
        doctorProfile.setLicense(doctorDto.license());
        doctorProfile.setLicenseIssueDate(doctorDto.licenseIssueDate());
        doctorProfile.setLicenseExpiryDate(doctorDto.licenseExpiryDate());

        Set<Specialization> specs = new HashSet<>(specializationRepository
                .findAllById(doctorDto.specializationIds()));
        doctorProfile.setSpecializations(specs);

        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFullName(),
                user.getRole(),
                doctorMapper.toDto(user.getDoctorProfile()),
                contactMapper.toDto(user.getContacts())
        );
    }
}
