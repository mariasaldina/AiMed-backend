package com.aimed.aimed.doctorsearch;

import com.aimed.aimed.chat.entity.Chat;
import com.aimed.aimed.doctorsearch.entity.DoctorWithSimilarity;
import com.aimed.aimed.doctorsearch.entity.SearchCriteria;
import com.aimed.aimed.embedding.EmbeddingModelClient;
import com.aimed.aimed.user.UserService;
import com.aimed.aimed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

import static io.micrometer.common.util.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class DoctorSearchService {

    private final EmbeddingModelClient embeddingModelClient;
    private final DoctorSearchRepository repository;
    private final UserService userService;

    private double calculateScore(DoctorWithSimilarity doctor) {
        long experience = ChronoUnit.YEARS.between(LocalDate.now(), doctor.practiceStartDate());
        double experienceScore = Math.min(1.0, experience / 10.0);
        double profileCompleteness =
                (isNotBlank(doctor.education()) ? 0.5 : 0.0)
                        + (isNotBlank(doctor.description()) ? 0.5 : 0.0);
        return 0.80 * doctor.similarity() + 0.15 * experienceScore + 0.05 * profileCompleteness;
    }

    public List<User> recommendDoctors(Chat chat) {
        float[] contextEmbedding;
        if (chat.getLastUserMessageAt() != null
                && chat.getLastDoctorSearchAt() != null
                && chat.getLastUserMessageAt().isBefore(chat.getLastDoctorSearchAt())) {
            contextEmbedding = chat.getContextEmbedding();
        } else {
            contextEmbedding = embeddingModelClient.getEmbedding(chat.getContext());
        }

        List<DoctorWithSimilarity> doctors = repository.findTopDoctorsByEmbedding(
                contextEmbedding,
                SearchCriteria.builder()
                        .filterLicense(true)
                        .filterBlankDescription(false)
                        .filterBlankEducation(false)
                        .orderByExperience(true)
                        .build()
        );

        return doctors.stream()
                .sorted(Comparator.comparing(this::calculateScore))
                .limit(5)
                .map(d -> userService.getUser(d.id()))
                .toList();
    }
}
