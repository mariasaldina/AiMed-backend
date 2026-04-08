package com.aimed.aimed.user;

import com.aimed.aimed.user.dto.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponseDto getUser(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        return this.userService.findById(userId);
    }

    @PutMapping("/patient-questionnaire")
    public UserResponseDto fillInPatientQuestionnaire(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody() UpdatePatientDto reqBody
    ) {
        Long userId = Long.valueOf(jwt.getSubject());
        return this.userService.fillInPatientQuestionnaire(userId, reqBody);
    }

    @PutMapping("/doctor-questionnaire")
    public UserResponseDto fillInDoctorQuestionnaire(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody() UpdateDoctorDto reqBody
    ) {
        Long userId = Long.valueOf(jwt.getSubject());
        return this.userService.fillInDoctorQuestionnaire(userId, reqBody);
    }
}
