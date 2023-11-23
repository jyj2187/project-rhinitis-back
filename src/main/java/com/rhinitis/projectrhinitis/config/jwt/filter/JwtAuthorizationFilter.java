package com.rhinitis.projectrhinitis.config.jwt.filter;

import com.auth0.jwt.JWT;
import com.rhinitis.projectrhinitis.config.auth.PrincipalDetails;
import com.rhinitis.projectrhinitis.config.jwt.JwtProvider;
import com.rhinitis.projectrhinitis.config.redis.RedisUtils;
import com.rhinitis.projectrhinitis.member.entity.Member;
import com.rhinitis.projectrhinitis.member.repository.MemberRepository;
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

// TODO: OncePerRequestFilter로 교체 고려.
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RedisUtils redisUtils;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, MemberRepository memberRepository, JwtProvider jwtProvider, RedisUtils redisUtils) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
        this.jwtProvider = jwtProvider;
        this.redisUtils = redisUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("Jwt Authorization Filter");
        String accessToken = jwtProvider.resolveToken(request.getHeader(jwtProvider.getHeader()));

        log.info("request url : {}", request.getRequestURI());

        validateToken(accessToken, response);

        log.info("Access token 검증 과정 종료");
        chain.doFilter(request, response);
    }

    private void validateToken(String accessToken, HttpServletResponse response) {
        if (accessToken != null) {
            log.info("Access token 검증");
            if (redisUtils.isBlocked(accessToken)) {
//                throw new RuntimeException("강탈당한 토큰");
                response.setStatus(401);
                return;
            }

            Map<String, Object> claims = jwtProvider.getClaimsFromToken(accessToken, "access");
            Member member;

            if (jwtProvider.isTokenExpired(JWT.decode(accessToken))) {
                log.info("Access token 만료");
                Long memberId = (Long) claims.get("id");
                String refreshToken = jwtProvider.resolveToken(redisUtils.getRefreshToken(memberId));
                log.info("Refresh token 검증");

                if (refreshToken == null || jwtProvider.isTokenExpired(JWT.decode(refreshToken))) {
//                    throw new RuntimeException("만료된 토큰");
                    response.setStatus(401);
                    return;
                }

                log.info("Access token 재발급");
                member = memberRepository.findByUsername((String) claims.get("username")).orElseThrow(() -> new RuntimeException("Member not found."));

                String newAccessToken = jwtProvider.createAccessToken(member);
                response.setHeader(jwtProvider.getHeader(), newAccessToken);

            } else {
                log.info("Access token 검증 완료");
                member = memberRepository.findByUsername((String) claims.get("username")).orElseThrow(() -> new RuntimeException("Member not found."));
            }

            log.info("Set Authentication ");
            PrincipalDetails principalDetails = new PrincipalDetails(member);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
