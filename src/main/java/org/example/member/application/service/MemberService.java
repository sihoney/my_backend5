package org.example.member.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.member.application.exception.DuplicateEmailException;
import org.example.member.application.exception.DuplicatePhoneException;
import org.example.member.application.usecase.MemberUsecase;
import org.example.member.domain.model.Member;
import org.example.member.domain.repository.MemberRepository;
import org.example.member.presentation.dto.request.MemberReq;
import org.example.member.presentation.dto.response.MemberAdmRes;
import org.example.member.presentation.dto.response.MemberRes;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService implements MemberUsecase {

    private final MemberRepository memberRepository;

    @Override
    public List<MemberRes> findAll() {
        return memberRepository.findAll().stream()
                .map(this::changeMemberResType)
                .toList();
    }

    @Override
    public List<MemberAdmRes> findAdmAll() {
        return memberRepository.findAll().stream()
                .map(this::changeMemberAdmResType)
                .toList();
    }

    @Transactional
    @Override
    public MemberRes save(MemberReq memberReq) {
        if(memberRepository.findByPhone(memberReq.phone())) {
//            throw new IllegalArgumentException("이미 존재하는 전화번호입니다.");
            throw new DuplicatePhoneException("이미 존재하는 전화번호입니다.");
        }

        if(memberRepository.findByEmail(memberReq.email())) {
//            throw new IllegalArgumentException("이미 존재하는 전화번호입니다.");
            throw new DuplicateEmailException("이미 존재하는 전화번호입니다.");
        }

        // 1. salt 생성
        // salt: 비밀번호를 해싱할 때 랜덤 값을 추가하는 것
        SecureRandom random = new SecureRandom();
        byte[] saltkey = random.generateSeed(8);

        Member member =Member.create(memberReq.email(), memberReq.name(), memberReq.address(),
                memberReq.status(), memberReq.password(), memberReq.phone());

        // 2. salt를 문자열로 변환 (byte[] --> Base64 문자열)
        member.setSaltKey(Base64.getEncoder().encodeToString(saltkey));
        log.info("saltkey : {}", member.getSaltKey());

        // 3. 비밀번호 + salt 결합 후 해싱
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        member.setPassword(encoder.encode(memberReq.password()+member.getSaltKey()));

        // 4. 회원 저장
        memberRepository.save(member);
        return new MemberRes(
                member.getId(), member.getName(), member.getAddress());
    }

    private MemberRes changeMemberResType(Member member){
        return new MemberRes(
                member.getId(),
                member.getName(),
                member.getAddress()
        );
    }
    private MemberAdmRes changeMemberAdmResType(Member member){
        return new MemberAdmRes(
                member.getId(),
                member.getEmail(),
                member.getName(),
                member.getPhone(),
                member.getAddress(),
                member.getStatus(),
                member.getRegId(),
                member.getRegDt(),
                member.getModifyId(),
                member.getModifyDt(),
                member.getFlag()
        );
    }
}
