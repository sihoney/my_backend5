package org.example.member.presentation.dto.request;

public record LoginRequest(
        String email,
        String password
) {
}
