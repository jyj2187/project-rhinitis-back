package com.rhinitis.projectrhinitis.member.service;

import com.rhinitis.projectrhinitis.member.dto.MemberDto;
import org.springframework.security.core.Authentication;

public interface MemberService {
    MemberDto.Response joinMember(MemberDto.Join joinDto);
//    MemberDto.Response loginMember(MemberDto.Login loginDto);
    MemberDto.Response loginMember(Authentication authentication);
    void sendActivationCode(MemberDto.Activate activateDto);
    MemberDto.Response activateMember(MemberDto.Activate activateDto);
    MemberDto.Response updateMember(Long memberId, MemberDto.Patch patchDto, Authentication authentication);
    MemberDto.Response getMember(Long memberId);
    void deactivateMember(Long memberId, Authentication authentication);
}
