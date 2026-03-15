package org.example.member.presentation.dto.request;

public record MemberReq(
        String email,
        String name,
        String password,
        String phone,
        String address,
        String status
) {
}
