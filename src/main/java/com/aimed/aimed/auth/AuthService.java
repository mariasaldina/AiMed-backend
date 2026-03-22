package com.aimed.aimed.auth;

import com.aimed.aimed.auth.dto.SignUpResponseDto;
import com.aimed.aimed.auth.dto.Tokens;
import com.aimed.aimed.auth.jwt.JwtService;
import com.aimed.aimed.auth.refresh.RefreshToken;
import com.aimed.aimed.auth.refresh.RefreshTokenRepository;
import com.aimed.aimed.auth.user.UserDetailsImpl;
import com.aimed.aimed.user.UserService;
import com.aimed.aimed.user.entity.User;
import com.aimed.aimed.user.enums.UserRole;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshRepo;
    private final JwtDecoder jwtDecoder;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            UserService userService,
            PasswordEncoder passwordEncoder,
            RefreshTokenRepository refreshRepo,
            JwtDecoder jwtDecoder
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.refreshRepo = refreshRepo;
        this.jwtDecoder = jwtDecoder;
    }

    public Tokens generateTokens(Long userId, String role) {
        String accessToken = jwtService.generateAccessToken(userId, role);
        String refreshToken = jwtService.generateRefreshToken(userId, role);
        String csrfToken = UUID.randomUUID().toString();

        this.refreshRepo.save(new RefreshToken(refreshToken, userId, OffsetDateTime.now().plusDays(7)));
        return new Tokens(accessToken, refreshToken, csrfToken);
    }

    public Tokens login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        String role = auth.getAuthorities().iterator().next().getAuthority();
        UserDetailsImpl user = (UserDetailsImpl) auth.getPrincipal();
        return generateTokens(user.getId(), role);
    }

    public SignUpResponseDto createUser(String username, String password, UserRole role) {
        if (this.userService.findByUsername(username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        String hashed = passwordEncoder.encode(password);
        User user = this.userService.create(username, hashed, role);

        return new SignUpResponseDto(user.getId(), user.getUsername(), user.getRole());
    }

    public Tokens refresh(String token) {
        RefreshToken found = this.refreshRepo.findByToken(token).orElseThrow(() ->
                new BadCredentialsException("Revoked refresh token")
        );
        this.refreshRepo.delete(found);

        if (found.getExpiresAt().isBefore(OffsetDateTime.now())) {
            throw new BadCredentialsException("Expired refresh token");
        }

        try {
            Jwt jwt = jwtDecoder.decode(token);

            if (!jwt.getClaimAsString("type").equals("refresh")) {
                throw new BadCredentialsException("Invalid token type");
            }
            String username = jwt.getSubject();
            String role = jwt.getClaimAsString("role");

            return this.generateTokens(found.getUserId(), role);
        } catch (JwtException e) {
            throw new BadCredentialsException("Invalid refresh token");
        }
    }

    @Transactional
    public void logout(String token) {
        this.refreshRepo.deleteByToken(token);
    }
}
