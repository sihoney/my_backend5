package org.example.member.presentation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.member.application.usecase.MemberUsecase;
import org.example.member.presentation.dto.request.LoginRequest;
import org.example.member.presentation.dto.request.MemberReq;
import org.example.member.presentation.dto.response.MemberAdmRes;
import org.example.member.presentation.dto.response.MemberRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@Tag(name = "Member", description = "사용자 CRUD API")
@RequiredArgsConstructor
public class MemberController {

    public final MemberUsecase memberUseCase;

    @GetMapping("findAll")
    @Operation(summary="사용자 목록 조회", description="사용자 목록을 조회합니다")
    public ResponseEntity<List<MemberRes>> getAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberUseCase.findAll());
    }

    @GetMapping("findAdmAll")
    @Operation(summary="사용자 목록 조회(어드민용)", description="어드민 권한으로 사용자 목록을 조회합니다")
    public ResponseEntity<List<MemberAdmRes>> getAdmAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberUseCase.findAdmAll());
    }

    @PostMapping("join")
    @Operation(summary="사용자 생성", description="사용자을 생성합니다.")
    public ResponseEntity<MemberRes> join(
            @RequestBody MemberReq memberReq
    ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(memberUseCase.save(memberReq));
    }

    @PostMapping("login")
    @Operation(summary="로그인", description="")
    public ResponseEntity<Boolean> login(
            @RequestBody LoginRequest request
    ){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(memberUseCase.login(request));
    }
}
