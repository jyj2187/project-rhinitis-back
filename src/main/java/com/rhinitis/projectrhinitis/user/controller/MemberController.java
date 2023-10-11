package com.rhinitis.projectrhinitis.user.controller;

import com.rhinitis.projectrhinitis.user.dto.MemberDto;
import com.rhinitis.projectrhinitis.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity joinMember(@RequestBody MemberDto.Join joinDto) {
        MemberDto.Response response = memberService.joinMember(joinDto);
        return new ResponseEntity<>(response ,HttpStatus.CREATED);
    }


    // 로그인
    @PostMapping("/login")
    public ResponseEntity loginMember(@RequestBody MemberDto.Login loginDto) {
        MemberDto.Response response = memberService.loginMember(loginDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 멤버 정보 조회
    @GetMapping("/{memberId}")
    public ResponseEntity getMember(@PathVariable Long memberId) {
        MemberDto.Response response = memberService.getMember(memberId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    // 멤버 정보 수정
    @PatchMapping("/{memberId}")
    public ResponseEntity patchMember(@PathVariable Long memberId, @RequestBody MemberDto.Patch patchDto) {
        MemberDto.Response response = memberService.updateMember(memberId, patchDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 멤버 비활성화
    @DeleteMapping("/{memberId}")
    public ResponseEntity inactiveMember(@PathVariable Long memberId) {
        memberService.inactiveMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
