package com.rhinitis.projectrhinitis.config.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rhinitis.projectrhinitis.config.jwt.JwtProvider;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.config.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.setFilterProcessesUrl("/members/v1/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return authentication;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication");

        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        Member member = principalDetails.getMember();
        // member not active 체크
//        if(member.getMemberStatus().name().equals(MemberStatus.INACTIVE.name())) {
//            throw new 어쩌구
//        }

        Long memberId = member.getMemberId();
        String username = member.getUsername();
        String accessToken = jwtProvider.createAccessToken(memberId, username, member.getDisplayName());
        response.addHeader(jwtProvider.getHeader(), accessToken);
        chain.doFilter(request, response);
    }
}
