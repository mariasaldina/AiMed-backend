package com.aimed.aimed.dev;

import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Profile("dev")
@RestController()
@RequestMapping("/dev/user")
@RequiredArgsConstructor
public class DevUserController {

    private final DevUserService devUserService;

    @PostMapping("/seed/doctors")
    public void seedDoctors(@RequestParam(required = false) Integer count) {
        if (count == null) {
            devUserService.generateDoctors(10);
        } else if (count < 1 || count > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Count should be from 1 to 20");
        } else {
            devUserService.generateDoctors(count);
        }
    }
}
