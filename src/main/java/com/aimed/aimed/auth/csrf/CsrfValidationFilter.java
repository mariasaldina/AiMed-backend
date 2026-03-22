package com.aimed.aimed.auth.csrf;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;

public class CsrfValidationFilter extends OncePerRequestFilter {

    private static final String CSRF_COOKIE_NAME = "csrf";
    private static final String CSRF_HEADER_NAME = "X-XSRF-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        Cookie csrfCookie = WebUtils.getCookie(req, CSRF_COOKIE_NAME);
        if (csrfCookie == null) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF cookie missing");
            return;
        }

        String headerToken = req.getHeader(CSRF_HEADER_NAME);
        if (headerToken == null || !headerToken.equals(csrfCookie.getValue())) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token mismatch");
            return;
        }

        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/auth/");
    }
}