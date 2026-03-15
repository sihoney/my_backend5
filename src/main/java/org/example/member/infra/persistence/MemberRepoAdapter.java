package org.example.member.infra.persistence;

import lombok.RequiredArgsConstructor;
import org.example.member.domain.model.Member;
import org.example.member.domain.repository.MemberRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepoAdapter implements MemberRepository {

    public final MemberJpaRepository memberJpaRepository;

    @Override
    public List<Member> findAll() {
        return memberJpaRepository.findAll();
    }

    @Override
    public Member save(Member member) {
        return memberJpaRepository.save(member);
    }

    @Override
    public boolean findByPhone(String phone) {
        return memberJpaRepository.findByPhone(phone).isPresent();
    }

    @Override
    public boolean findByEmail(String email) {
        return memberJpaRepository.findByEmail(email).isPresent();
    }
}
