package org.example.member.infra.persistence;

import org.example.member.domain.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MemberJpaRepository extends JpaRepository<Member, UUID> {
    Optional<Member> findByPhone(String phone);
    Optional<Member> findByEmail(String email);
}
