package com.aimed.aimed.auth;

import com.aimed.aimed.auth.dto.LoginDto;
import com.aimed.aimed.auth.dto.SignUpDto;
import com.aimed.aimed.auth.dto.SignUpResponseDto;
import com.aimed.aimed.auth.dto.Tokens;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto req, HttpServletResponse res) {
        Tokens tokens = this.authService.login(req.username(), req.password());
        setCookies(res, "access", tokens.access(), true);
        setCookies(res, "refresh", tokens.refresh(), true);
        setCookies(res, "csrf", tokens.csrf(), false);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpDto req, HttpServletResponse res) {
        SignUpResponseDto user = this.authService.createUser(req.username(), req.password(), req.role());

        Tokens tokens = this.authService.generateTokens(user.id(), user.role().toString());
        setCookies(res, "access", tokens.access(), true);
        setCookies(res, "refresh", tokens.refresh(), true);
        setCookies(res, "csrf", tokens.csrf(), false);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/refresh")
    public void refresh(HttpServletRequest req, HttpServletResponse res) {
        Cookie refresh = WebUtils.getCookie(req, "refresh");
        if (refresh == null) {
            throw new BadCredentialsException("No refresh token");
        }

        Tokens tokens = this.authService.refresh(refresh.getValue());
        setCookies(res, "access", tokens.access(), true);
        setCookies(res, "refresh", tokens.refresh(), true);
        setCookies(res, "csrf", tokens.csrf(), false);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest req, HttpServletResponse res) {
        Cookie refreshCookie = WebUtils.getCookie(req, "refresh");

        if (refreshCookie != null) {
            this.authService.logout(refreshCookie.getValue());
        }

        setCookies(res, "access", "", true);
        setCookies(res, "refresh", "", true);
        setCookies(res, "csrf", "", false);
    }

    private void setCookies(
            HttpServletResponse res,
            String name,
            String value,
            Boolean httpOnly
    ) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(httpOnly);
        cookie.setPath("/");
        res.addCookie(cookie);
    }
}
