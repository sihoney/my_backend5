package org.example.member.domain.repository;

import org.example.member.domain.model.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll();

    Member save(Member member);

    boolean findByPhone(String phone);

    boolean findByEmail(String email);
}
