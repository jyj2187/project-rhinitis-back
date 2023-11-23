package com.rhinitis.projectrhinitis.config.security;

import com.rhinitis.projectrhinitis.config.jwt.JwtProvider;
import com.rhinitis.projectrhinitis.config.redis.RedisUtils;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RedisUtils redisUtils;
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("onLogoutSuccess");
        String resolvedToken = (String) request.getAttribute(jwtProvider.getHeader());

        Long memberId = jwtProvider.getMemberIdFromToken(resolvedToken);
        String username = jwtProvider.getUsernameFromToken(resolvedToken);
        redisUtils.deleteRefreshToken(memberId);
        log.info("memberId : {}의 refresh token 삭제", memberId);

        Member logoutMember = memberRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("우리 회원 아닙니다."));
        log.info("logoutMember : {}", logoutMember.getUsername());
        
        // TODO: 로그인 상태 업데이트 처리 및 204 상태 보내기
        response.setStatus(204);
    }
}
