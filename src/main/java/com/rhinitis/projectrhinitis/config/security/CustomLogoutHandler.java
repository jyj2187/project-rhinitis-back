package com.rhinitis.projectrhinitis.config.security;

import com.rhinitis.projectrhinitis.config.jwt.JwtProvider;
import com.rhinitis.projectrhinitis.config.redis.RedisUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomLogoutHandler implements LogoutHandler {

    private final RedisUtils redisUtils;
    private final JwtProvider jwtProvider;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(jwtProvider.getHeader());
        String resolvedToken = jwtProvider.resolveToken(token);
        Long expire = jwtProvider.getExpire(token);
        // isBlocked 체크 필요할까?
        log.info("resolvedToken : {}, expire : {} ", resolvedToken, expire);
        redisUtils.addBlockList(resolvedToken, expire);
        log.info("add access token in blocklist");
        request.setAttribute(jwtProvider.getHeader(), resolvedToken);
    }
}
