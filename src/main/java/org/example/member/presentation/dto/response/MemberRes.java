package org.example.member.presentation.dto.response;

import java.util.UUID;

public record MemberRes(
        UUID id,
        String name,
        String address
) {
}
