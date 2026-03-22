package com.aimed.aimed.user;

import com.aimed.aimed.user.dto.ContactDto;
import com.aimed.aimed.user.dto.DoctorDto;
import com.aimed.aimed.user.dto.PatientDto;
import com.aimed.aimed.user.dto.UserDto;
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
    public UserDto getUser(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        return this.userService.findById(userId);
    }

    @PutMapping("/patient-questionnaire")
    public void fillInPatientQuestionnaire(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody() PatientDto reqBody
    ) {
        Long userId = Long.valueOf(jwt.getSubject());
        this.userService.fillInPatientQuestionnaire(userId, reqBody);
    }

    @PutMapping("/doctor-questionnaire")
    public void fillInDoctorQuestionnaire(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody() DoctorDto reqBody
    ) {
        Long userId = Long.valueOf(jwt.getSubject());
        this.userService.fillInDoctorQuestionnaire(userId, reqBody);
    }

    @PutMapping("/contacts")
    public void updateContacts(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody() List<ContactDto> contacts
    ) {
        Long userId = Long.valueOf(jwt.getSubject());
        this.userService.updateContacts(userId, contacts);
    }
}
