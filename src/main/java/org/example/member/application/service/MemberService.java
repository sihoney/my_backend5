package org.example.member.application.service;

import lombok.RequiredArgsConstructor;
import org.example.member.application.usecase.MemberUsecase;
import org.example.member.domain.model.Member;
import org.example.member.domain.repository.MemberRepository;
import org.example.member.presentation.dto.request.MemberReq;
import org.example.member.presentation.dto.response.MemberAdmRes;
import org.example.member.presentation.dto.response.MemberRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
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

    @Override
    public MemberRes save(MemberReq memberReq) {
        if(!memberRepository.findByPhone(memberReq.phone())) {
            Member member = memberRepository.save(Member.create(
                    memberReq.email(),
                    memberReq.name(),
                    memberReq.address(),
                    memberReq.status(),
                    memberReq.password(),
                    memberReq.phone()
            ));
            return new MemberRes(
                    member.getId(),
                    member.getName(),
                    member.getAddress()
            );
        } else {
            //TODO: throw
        }

        return null;
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
