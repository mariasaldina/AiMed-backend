package com.aimed.aimed.specialization;

import com.aimed.aimed.ollama.enums.Prompt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
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
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(getAll().stream().toList());
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to serialize specializations"
            );
        }
    }

    public Set<Long> getAllIds() {
        return specializations
                .stream()
                .map(Specialization::getId)
                .collect(Collectors.toSet());
    }

    public List<Long> getAllIdsByNames(Set<String> names) {
        return specializations
                .stream()
                .filter(s -> names.contains(s.getName()))
                .map(Specialization::getId)
                .toList();
    }
}
