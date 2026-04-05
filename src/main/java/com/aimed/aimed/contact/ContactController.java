package com.aimed.aimed.contact;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/contacts")
@RestController
public class ContactController {

    private final ContactService contactService;

    @GetMapping()
    public ContactsDto getContacts(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        return this.contactService.getContacts(userId);
    }

    @PutMapping()
    public void updateContacts(@AuthenticationPrincipal Jwt jwt, @RequestBody ContactsDto dto) {
        Long userId = Long.valueOf(jwt.getSubject());
        this.contactService.updateContacts(userId, dto);
    }
}
