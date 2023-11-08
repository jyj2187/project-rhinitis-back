package com.rhinitis.projectrhinitis.member.controller;

import com.rhinitis.projectrhinitis.member.dto.MemberDto;
import com.rhinitis.projectrhinitis.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * 회원 컨트롤러 V1
 * 회원가입, 로그인, 정보 조회
 * TODO:
 *  - check 메서드 작성
 *  - 기능 별 컨트롤러 분리 고려
 *  - 처음 로그인한 멤버 인증 메서드
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/members/v1")
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity joinMember(@RequestBody MemberDto.Join joinDto) {
        MemberDto.Response response = memberService.joinMember(joinDto);
        return new ResponseEntity<>(response ,HttpStatus.CREATED);
    }

//    // 아이디 중복확인
//    @PostMapping("/{memberId}/checkUsername")
//    public ResponseEntity checkUsername() {
//        return null;
//    }
//
//    // 닉네임 중복확인
//    @PostMapping("/{memberId}/checkNickname")
//    public ResponseEntity checkNickname() {
//        return null;
//    }
//
//    // 이메일 중복확인
//    @PostMapping("/{memberId}/checkEmail")
//    public ResponseEntity checkEmail() {
//        return null;
//    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity loginMember(Authentication authentication) {
        log.info("authentication : " + authentication.getPrincipal());
        MemberDto.Response response = memberService.loginMember(authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 멤버 활성화
    @PostMapping("/{memberId}/activate")
    public ResponseEntity activateMember(@PathVariable Long memberId, @RequestBody MemberDto.Activate activateDto) {
        MemberDto.Response response = memberService.activateMember(memberId, activateDto);
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
    public ResponseEntity patchMember(@PathVariable Long memberId, @RequestBody MemberDto.Patch patchDto, Authentication authentication) {
        MemberDto.Response response = memberService.updateMember(memberId, patchDto, authentication);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 멤버 비활성화
    @DeleteMapping("/{memberId}")
    public ResponseEntity deactivateMember(@PathVariable Long memberId, Authentication authentication) {
        memberService.deactivateMember(memberId, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
