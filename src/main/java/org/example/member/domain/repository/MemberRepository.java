package org.example.member.domain.repository;

import org.example.member.domain.model.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    List<Member> findAll();

    Member save(Member member);

    boolean findByPhone(String phone);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);
}
