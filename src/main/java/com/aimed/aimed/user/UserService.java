package com.aimed.aimed.user;

import com.aimed.aimed.specialization.Specialization;
import com.aimed.aimed.specialization.SpecializationRepository;
import com.aimed.aimed.user.dto.ContactDto;
import com.aimed.aimed.user.dto.DoctorDto;
import com.aimed.aimed.user.dto.PatientDto;
import com.aimed.aimed.user.dto.UserDto;
import com.aimed.aimed.user.entity.*;
import com.aimed.aimed.user.enums.UserRole;
import com.aimed.aimed.user.repository.DoctorProfileRepository;
import com.aimed.aimed.user.repository.PatientProfileRepository;
import com.aimed.aimed.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PatientProfileRepository patientRepository;
    private final DoctorProfileRepository doctorRepository;
    private final SpecializationRepository specializationRepository;

    public UserService(
            UserRepository userRepository,
            PatientProfileRepository patientRepository,
            DoctorProfileRepository doctorRepository,
            SpecializationRepository specializationRepository
    ) {
        this.userRepository = userRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.specializationRepository = specializationRepository;
    }

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

    public UserDto findById(Long id) {
        User user = this.userRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        return new UserDto(user.getId(), user.getUsername(), user.getRole());
    }

    @Transactional
    public void fillInPatientQuestionnaire(Long userId, PatientDto patientDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
        if (user.getRole() != UserRole.PATIENT) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not a patient");
        }

        PatientProfile patientProfile = patientRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        patientProfile.setAddress(patientDto.address());
        patientProfile.setBirthdate(patientDto.birthdate());
        patientProfile.setGender(patientDto.gender());
        patientProfile.setMedicalHistory(patientDto.medicalHistory());
    }

    @Transactional
    public void fillInDoctorQuestionnaire(Long userId, DoctorDto doctorDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));
        if (user.getRole() != UserRole.DOCTOR) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not a doctor");
        }

        DoctorProfile doctorProfile = doctorRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

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
    }

    @Transactional
    public void updateContacts(Long userId, List<ContactDto> contacts) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No such user"));

        List<UserContact> newContacts = contacts.stream()
                .map(c ->
                        new UserContact(user, c.type(), c.value(), c.isPrimary()))
                .toList();

        user.getContacts().clear();
        user.getContacts().addAll(newContacts);
    }
}
