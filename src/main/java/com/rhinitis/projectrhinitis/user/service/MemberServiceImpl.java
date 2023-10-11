package com.rhinitis.projectrhinitis.user.service;

import com.rhinitis.projectrhinitis.user.dto.MemberDto;
import com.rhinitis.projectrhinitis.user.entity.Member;
import com.rhinitis.projectrhinitis.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    public MemberDto.Response joinMember(MemberDto.Join joinDto) {
        verifyExistUsername(joinDto.getUsername());
        Member member = joinDto.toEntity();
        Member savedMember = memberRepository.save(member);
        log.info("새로운 회원 \"{}\" 가입. 회원 ID : {} ", savedMember.getUsername(), savedMember.getMemberId());
        return new MemberDto.Response(savedMember);
    }

    @Override
    public MemberDto.Response loginMember(MemberDto.Login loginDto) {
        Member existMember = verifyExistMemberByUsername(loginDto.getUsername());
        if (!loginDto.getPassword().equals(existMember.getPassword())) {
            throw new RuntimeException("비밀번호가 틀리잖아!");
        }
        log.info("회원 \"{}\" 로그인. 회원 ID : {} ", existMember.getUsername(), existMember.getMemberId());
        return new MemberDto.Response(existMember);
    }

    @Override
    public MemberDto.Response updateMember(Long memberId, MemberDto.Patch patchDto) {
        log.info("회원 수정. 회원 ID : {}", memberId);
        Member member = verifyExistMemberById(memberId);
        member.updateMember(patchDto);
        Member savedMember = memberRepository.save(member);
        return new MemberDto.Response(savedMember);
    }

    @Override
    public MemberDto.Response getMember(Long memberId) {
        log.info("회원 조회. 회원 ID : {}", memberId);
        Member member = verifyExistMemberById(memberId);
        return new MemberDto.Response(member);
    }

    @Override
    public void inactiveMember(Long memberId) {
        Member member = verifyExistMemberById(memberId);
        member.inactive();
        memberRepository.save(member);
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
