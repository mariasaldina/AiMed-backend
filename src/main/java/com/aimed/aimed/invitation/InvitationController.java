package com.aimed.aimed.invitation;

import com.aimed.aimed.invitation.dto.InvitationRequestDto;
import com.aimed.aimed.invitation.dto.InvitationResponseDto;
import com.aimed.aimed.invitation.enums.InvitationStatus;
import com.aimed.aimed.message.dto.MessageDto;
import com.aimed.aimed.user.enums.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invitations")
@RequiredArgsConstructor
public class InvitationController {

    private final InvitationService invitationService;

    @GetMapping()
    public List<InvitationResponseDto> getAll(@AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        UserRole role = UserRole.valueOf(jwt.getClaimAsString("role").replace("ROLE_", ""));
        return this.invitationService.getAll(userId, role);
    }

    @PostMapping("/invite")
    public MessageDto inviteDoctor(@AuthenticationPrincipal Jwt jwt, @RequestBody InvitationRequestDto dto) {
        Long patientId = Long.valueOf(jwt.getSubject());
        return this.invitationService.inviteDoctor(patientId, dto);
    }

    public record DoctorsResponseDto(
            InvitationStatus status
    ) {}

    @PostMapping("/{invitationId}/answer")
    public void saveDoctorsResponse(
            @AuthenticationPrincipal Jwt jwt,
            @PathVariable("invitationId") Long invitationId,
            @RequestBody DoctorsResponseDto dto
    ) {
        Long doctorId = Long.valueOf(jwt.getSubject());
        this.invitationService.saveDoctorsResponse(doctorId, invitationId, dto.status);
    }
}
