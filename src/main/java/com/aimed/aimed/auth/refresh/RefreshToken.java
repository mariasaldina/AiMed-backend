package com.aimed.aimed.auth.refresh;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "refresh_tokens")
@Getter
@Setter
@NoArgsConstructor
public class RefreshToken {

    public RefreshToken(String token, Long userId, OffsetDateTime expiresAt) {
        this.token = token;
        this.userId = userId;
        this.expiresAt = expiresAt;
    }

    @Id
    @NotNull
    private String token;

    @NotNull
    private OffsetDateTime expiresAt;

    @NotNull
    private Long userId;
}
