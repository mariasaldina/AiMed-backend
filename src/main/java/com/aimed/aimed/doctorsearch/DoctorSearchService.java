package com.aimed.aimed.doctorsearch;

import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.doctorsearch.entity.DoctorWithSimilarity;
import com.aimed.aimed.doctorsearch.entity.SearchCriteria;
import com.aimed.aimed.embedding.EmbeddingModelClient;
import com.aimed.aimed.ollama.OllamaService;
import com.aimed.aimed.ollama.enums.Prompt;
import com.aimed.aimed.specialization.SpecializationsDictionaryService;
import com.aimed.aimed.user.UserService;
import com.aimed.aimed.user.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class DoctorSearchService {

    private final OllamaService ollamaService;
    private final EmbeddingModelClient embeddingModelClient;
    private final DoctorSearchRepository repository;
    private final UserService userService;
    private final SpecializationsDictionaryService specializationsDictionaryService;

    private double calculateScore(DoctorWithSimilarity doctor) {
        long experience = ChronoUnit.YEARS.between(LocalDate.now(), doctor.practiceStartDate());
        double experienceScore = Math.min(1.0, experience / 10.0);
        double profileCompleteness =
                (isNotBlank(doctor.education()) ? 0.5 : 0.0)
                        + (isNotBlank(doctor.description()) ? 0.5 : 0.0);
        return 0.80 * doctor.similarity() + 0.15 * experienceScore + 0.05 * profileCompleteness;
    }

    @Transactional
    public List<User> recommendDoctors(Chat chat) {
        String prompt = ollamaService.getPrompt(Prompt.EXTRACT_SPECIALIZATIONS)
                + "\nСпециальности:\n"
                + specializationsDictionaryService.getSpecializationsString()
                + "\nКонтекст:\n"
                + chat.getContext();
        List<Long> specializations;
        try {
            ObjectMapper mapper = new ObjectMapper();
            specializations = mapper.readValue(ollamaService.generatePromptAnswer(prompt),
                    new TypeReference<List<Long>>() {});
        } catch (JsonProcessingException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not parse Ollama response for context analysis");
        }

        float[] contextEmbedding;
        if (chat.getLastUserMessageAt() != null
                && chat.getLastDoctorSearchAt() != null
                && chat.getLastUserMessageAt().isBefore(chat.getLastDoctorSearchAt())
        ) {
            contextEmbedding = chat.getContextEmbedding();
        } else {
            contextEmbedding = embeddingModelClient.getEmbedding(chat.getContext());
            chat.setContextEmbedding(contextEmbedding);
        }

        List<DoctorWithSimilarity> doctors = repository.findTopDoctors(
                specializations,
                contextEmbedding,
                SearchCriteria.builder()
                        .filterBlankDescription(false)
                        .filterBlankEducation(false)
                        .orderByExperience(false)
                        .build()
        );

        return doctors.stream()
                .sorted(Comparator.comparing(this::calculateScore))
                .limit(5)
                .map(d -> userService.getUser(d.userId()))
                .toList();
    }
}
