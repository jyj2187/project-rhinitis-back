package com.rhinitis.projectrhinitis.config.jwt.filter;

import com.auth0.jwt.JWT;
import com.rhinitis.projectrhinitis.config.jwt.JwtProvider;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
import com.rhinitis.projectrhinitis.config.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, JwtProvider jwtProvider) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        String accessToken = request.getHeader("ACCESS_RHINITIS");
        log.info("Jwt Authorization Filter");
        String accessToken = request.getHeader(jwtProvider.getHeader());
        if (accessToken == null || !accessToken.startsWith("Bearer")) {
            chain.doFilter(request, response);
        } else if (jwtProvider.isTokenExpired(JWT.decode(accessToken.replace("Bearer ", "")))){
            // TODO: refresh token 구현
            log.info("access token expired.");
//            accessToken = accessToken.replace("Bearer ", "");
//            Map<String, Object> memberInfoMap = jwtProvider.getClaimsFromToken(accessToken, "accessKey");
//
//            Long memberId = (Long) memberInfoMap.get("id");
//
//            if
            chain.doFilter(request, response);
        } else {
            accessToken = accessToken.replace("Bearer ", "");
            Map<String, Object> map = jwtProvider.getClaimsFromToken(accessToken, "access");

            Member member = memberRepository.findByUsername((String) map.get("username")).orElseThrow(() -> new RuntimeException("Member not found."));
            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }
    }
}
