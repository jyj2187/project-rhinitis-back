package com.rhinitis.projectrhinitis.member.service;

import com.rhinitis.projectrhinitis.config.auth.PrincipalDetails;
import com.rhinitis.projectrhinitis.member.dto.MemberDto;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public MemberDto.Response joinMember(MemberDto.Join joinDto) {
        joinDto.encodePassword(passwordEncoder);
        verifyExistUsername(joinDto.getUsername());
        Member member = joinDto.toEntity();
        Member savedMember = memberRepository.save(member);
        log.info("새로운 회원 \"{}\" 가입. 회원 ID : {} ", savedMember.getUsername(), savedMember.getMemberId());
        return new MemberDto.Response(savedMember);
    }

    @Override
    public MemberDto.Response loginMember(Authentication authentication) {
        Member authenticatedMember = ((PrincipalDetails) authentication.getPrincipal()).getMember();
        log.info("회원 \"{}\" 로그인. 회원 ID : {} ", authenticatedMember.getUsername(), authenticatedMember.getMemberId());
        return new MemberDto.Response(authenticatedMember);
    }

    /**
     * <pre>회원 활성화 서비스</pre>
     * TODO: 활성화 인증 by 이메일, 문자, 톡 등을 통해 처리
     *
     * @param activateDto
     * @param memberId
     * @return
     */
    @Override
    public MemberDto.Response activateMember(Long memberId, MemberDto.Activate activateDto) {
        Member existMember = verifyExistMemberById(memberId);
        if (!activateDto.getUsername().equals(existMember.getUsername())) {
            throw new RuntimeException("올바르지 않은 요청입니다.");
        }
//        if(existMember.getMemberStatus().equals(MemberStatus.ACTIVE)) {
//            throw new RuntimeException("이미 활성화된 회원입니다.");
//        }

        // 임시용
        String checkActivationCode = "ACTIVATE";
        if (!activateDto.getActivationCode().equals(checkActivationCode)) {
            throw new RuntimeException("활성화 코드가 올바르지 않습니다.");
        }
        existMember.activate();
        memberRepository.save(existMember);
        return new MemberDto.Response(existMember);
    }

    @Override
    public MemberDto.Response updateMember(Long memberId, MemberDto.Patch patchDto, Authentication authentication) {
        log.info("회원 수정. 회원 ID : {}", memberId);
        Member existMember = verifyExistMemberById(memberId);
        existMember.updateMember(patchDto);
        Member savedMember = memberRepository.save(existMember);
        return new MemberDto.Response(savedMember);
    }

    @Override
    public MemberDto.Response getMember(Long memberId) {
        log.info("회원 조회. 회원 ID : {}", memberId);
        Member existMember = verifyExistMemberById(memberId);
        return new MemberDto.Response(existMember);
    }

    @Override
    public void deactivateMember(Long memberId, Authentication authentication) {
        Member existMember = verifyExistMemberById(memberId);
        existMember.deactivate();
        memberRepository.save(existMember);
        log.info("회원 비활성화. 회원 ID : {}", memberId);
    }

    private Member verifyExistMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("이딴 회원은 존재하지 않습니다."));
    }

    private Member verifyExistMemberByUsername(String username) {
        return memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("이딴 회원은 존재하지 않습니다."));
    }

    private void verifyExistUsername(String username) {
        Optional<Member> checkMember = memberRepository.findByUsername(username);
        if (checkMember.isPresent()) {
            log.error("이미 존재하는 username입니다. username : {}", username);
            throw new RuntimeException("이미 존재하는 회원이잖아!");
        }
    }
}
