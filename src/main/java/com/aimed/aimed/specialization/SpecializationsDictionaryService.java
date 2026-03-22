package com.aimed.aimed.specialization;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpecializationsDictionaryService {
    private final SpecializationRepository specializationRepository;
    private volatile Set<Specialization> specializations;

    public SpecializationsDictionaryService(
            SpecializationRepository specializationRepository
    ) {
        this.specializationRepository = specializationRepository;
    }

    @PostConstruct
    public void init() {
        reload();
    }

    public synchronized void reload() {
        specializations = new HashSet<>(specializationRepository.findAll());
    }

    public Set<Specialization> getAll() {
        return specializations;
    }

    public String getSpecializationsString() {
        return specializations
                .stream()
                .map(s -> s.getId() + " - " + s.getName())
                .collect(Collectors.joining("\n"));
    }

    public Set<Long> getAllIds() {
        return specializations
                .stream()
                .map(Specialization::getId)
                .collect(Collectors.toSet());
    }
}
