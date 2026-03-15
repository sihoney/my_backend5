package org.example.member.application.usecase;

import org.example.member.presentation.dto.request.MemberReq;
import org.example.member.presentation.dto.response.MemberAdmRes;
import org.example.member.presentation.dto.response.MemberRes;

import java.util.List;

public interface MemberUsecase {
    List<MemberRes> findAll();

    List<MemberAdmRes> findAdmAll();

    MemberRes save(MemberReq memberReq);
}
