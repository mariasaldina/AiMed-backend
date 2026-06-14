package com.aimed.aimed.dev;

import com.aimed.aimed.dev.dto.DoctorGenerationRequestDto;
import com.aimed.aimed.dev.dto.DoctorGenerationResponseDto;
import com.aimed.aimed.ollama.OllamaService;
import com.aimed.aimed.ollama.enums.Prompt;
import com.aimed.aimed.specialization.SpecializationsDictionaryService;
import com.aimed.aimed.user.UserService;
import com.aimed.aimed.user.dto.UpdateDoctorDto;
import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.UserRole;
import com.nimbusds.jose.util.Pair;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Function;
import java.util.stream.Collectors;

@Profile("dev")
@Service
@RequiredArgsConstructor
@Slf4j
public class DevUserService {

    private final Faker faker = new Faker(Locale.of("ru"));
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final OllamaService ollamaService;
    private final SpecializationsDictionaryService specializationsDictionaryService;

    private static final List<Pair<String, Integer>> SPECIALIZATIONS = List.of(
            Pair.of("Терапевт", 20),
            Pair.of("Педиатр", 15),
            Pair.of("Кардиолог", 10),
            Pair.of("Невролог", 8),
            Pair.of("Эндокринолог", 6),
            Pair.of("Гастроэнтеролог", 5),
            Pair.of("ЛОР", 5),
            Pair.of("Офтальмолог", 5),
            Pair.of("Дерматолог", 5),
            Pair.of("Гинеколог", 5),
            Pair.of("Уролог", 4),
            Pair.of("Хирург", 4),
            Pair.of("Стоматолог", 4),
            Pair.of("Психиатр", 3),
            Pair.of("Онколог", 2),
            Pair.of("Ревматолог", 2),
            Pair.of("Нефролог", 2),
            Pair.of("Аллерголог", 2),
            Pair.of("Инфекционист", 2),
            Pair.of("Физиотерапевт", 1)
    );

    private static final List<Pair<Integer, Integer>> SPECIALIZATION_COUNTS = List.of(
            Pair.of(1, 55),
            Pair.of(2, 30),
            Pair.of(3, 14),
            Pair.of(4, 1)
    );

    private static final List<Pair<Integer, Integer>> PRACTICE_YEARS = List.of(
            Pair.of(3, 25),
            Pair.of(7, 35),
            Pair.of(15, 30),
            Pair.of(25, 10)
    );

    public static <T> T weightedRandom(List<Pair<T, Integer>> items) {
        int total = items.stream()
                .mapToInt(Pair::getRight)
                .sum();
        int r = ThreadLocalRandom.current().nextInt(total);
        int current = 0;

        for (Pair<T, Integer> item : items) {
            current += item.getRight();
            if (r < current) {
                return item.getLeft();
            }
        }

        throw new IllegalStateException("Empty or invalid weights");
    }

    public User generateUser(UserRole role) {
        String firstName = faker.name().firstName();
        String lastName, username;
        do {
            lastName = faker.name().lastName();
            username = lastName.toLowerCase() + faker.number().digits(6);
        } while (userService.findByUsername(username).isPresent());

        System.out.println(firstName + " " + lastName + " " + username);
        return userService.create(
                username,
                passwordEncoder.encode("password"),
                role,
                firstName + " " + lastName
        );
    }

    @Transactional
    public void generateDoctors(int count) {
        List<DoctorGenerationRequestDto> doctors = new ArrayList<>();
        Map<Long, User> users = new HashMap<>();

        for (int i = 0; i < count; ++i) {
            User doctor = generateUser(UserRole.DOCTOR);
            users.put(doctor.getId(), doctor);

            Integer specializationsCount = weightedRandom(SPECIALIZATION_COUNTS);
            Set<String> specializations = new HashSet<>();
            while (specializations.size() < specializationsCount) {
                specializations.add(weightedRandom(SPECIALIZATIONS));
            }
            Integer practiceYears = weightedRandom(PRACTICE_YEARS);

            doctors.add(DoctorGenerationRequestDto.builder()
                            .id(doctor.getId())
                            .fullName(doctor.getFullName())
                            .practiceYears(practiceYears)
                            .specializations(specializations)
                    .build());
        }

        List<DoctorGenerationResponseDto> generated;
        try {
            ObjectMapper mapper = new ObjectMapper();
            String doctorsJson = mapper.writeValueAsString(doctors);
            log.info(doctorsJson);
            String raw = ollamaService.generatePromptAnswer(
                    ollamaService.getPrompt(Prompt.GENERATE_DOCTORS) + doctorsJson
            );
            log.info(raw);
            generated = mapper.readValue(raw, new TypeReference<List<DoctorGenerationResponseDto>>() {});
        } catch (JacksonException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Could not parse Ollama model response");
        }
        Map<Long, DoctorGenerationResponseDto> generatedById =
                generated.stream()
                        .collect(Collectors.toMap(
                                DoctorGenerationResponseDto::id,
                                Function.identity()
                        ));

        doctors.forEach(d -> {
            LocalDate practiceStartDate = LocalDate.now().minusYears(d.practiceYears());
            DoctorGenerationResponseDto rest = Optional.ofNullable(generatedById.get(d.id()))
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            "Invalid response was produces by Ollama model"
                    ));
            List<Long> specializationIds = specializationsDictionaryService
                    .getAllIdsByNames(d.specializations());

            UpdateDoctorDto dto = UpdateDoctorDto.builder()
                    .fullName(users.get(d.id()).getFullName())
                    .address(faker.address().fullAddress())
                    .description(rest.description())
                    .education(rest.education())
                    .practiceStartDate(practiceStartDate)
                    .specializationIds(specializationIds)
                    .build();
            userService.fillInDoctorQuestionnaire(d.id(), dto);
        });
    }
}
