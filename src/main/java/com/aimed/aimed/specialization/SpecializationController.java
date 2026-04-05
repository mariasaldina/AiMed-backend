package com.aimed.aimed.specialization;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/specialization")
public class SpecializationController {

    private final SpecializationsDictionaryService specializationsDictionaryService;

    @GetMapping
    public List<SpecializationDto> getAll() {
        return this.specializationsDictionaryService.getAll().stream()
                .map(s -> new SpecializationDto(s.getId(), s.getName()))
                .toList();
    }
}
