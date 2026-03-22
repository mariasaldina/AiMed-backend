package com.aimed.aimed.auth.dto;

public record Tokens(
        String access,
        String refresh,
        String csrf
) {}