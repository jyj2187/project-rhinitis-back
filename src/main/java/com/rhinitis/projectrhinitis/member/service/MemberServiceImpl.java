package com.rhinitis.projectrhinitis.member.service;

import com.rhinitis.projectrhinitis.config.auth.PrincipalDetails;
import com.rhinitis.projectrhinitis.member.dto.MemberDto;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@Transactional
//@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    @Value("${sms.FROM}")
    private String smsFrom;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final DefaultMessageService messageService;

    public MemberServiceImpl(MemberRepository memberRepository, PasswordEncoder passwordEncoder, @Value("${sms.API-KEY}") String smsApiKey, @Value("${sms.API-SECRETS}") String smsApiSecrets) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.messageService = NurigoApp.INSTANCE.initialize(smsApiKey, smsApiSecrets, "https://api.coolsms.co.kr");
    }

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

    @Override
    public void sendActivationCode(MemberDto.Activate activateDto) {
//        Member authenticatedMember = ((PrincipalDetails) authentication.getPrincipal()).getMember();
//        authenticatedMember.checkInRegister();

        Member existMember = memberRepository.findByUsername(activateDto.getUsername()).orElseThrow();
        existMember.checkInRegister();

        Message activationCode = new Message();
        activationCode.setFrom(smsFrom);
        activationCode.setTo(activateDto.getPhone());
        activationCode.setText(createActivationCode());
//        messageService.sendOne(new SingleMessageSendingRequest(activationCode));
    }

    @Override
    public MemberDto.Response activateMember(MemberDto.Activate activateDto) {
//        Member authenticatedMember = ((PrincipalDetails) authentication.getPrincipal()).getMember();
//        authenticatedMember.checkInRegister();
        Member existMember = memberRepository.findByUsername(activateDto.getUsername()).orElseThrow();
        existMember.checkInRegister();
        // TODO: 인증 완료시 전화번호 저장 -> 전화번호는 Member 테이블과 별도로 관리
        // 임시용
        String checkActivationCode = "000000";
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

    private String createActivationCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();
//        for (int i = 0; i < 6; i++) {
//            code.append(random.nextInt(9));
//        }
        code.append("000000");
        return code.toString();
    }
}
