package com.rhinitis.projectrhinitis.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.rhinitis.projectrhinitis.member.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class JwtProvider {
    @Value("${jwt.access}")
    private String accessKey;
    @Value("${jwt.refresh}")
    private String refreshKey;
    @Getter
    @Value("${jwt.header}")
    private String header;
    // 10분 (테스트용)
    private static final Date ACCESS_TOKEN_EXPIRES_AT = new Date(System.currentTimeMillis() + (1000 * 60 * 5));
    // 1시간 (테스트용)
    private static final Date REFRESH_TOKEN_EXPIRES_AT = new Date(System.currentTimeMillis() + (1000 * 60 * 10));
    public static final String BEARER_PREFIX = "Bearer ";

    public String createAccessToken(Long memberId, String username, String displayName) {
        String accessToken = JWT.create()
                .withSubject("rhinitis_access_token")
                .withExpiresAt(ACCESS_TOKEN_EXPIRES_AT)
                .withClaim("id", memberId)
                .withClaim("username", username)
//                .withClaim("email", email)
                .withClaim("displayName", displayName)
                .sign(Algorithm.HMAC512(accessKey));
        accessToken = BEARER_PREFIX + accessToken;
        return accessToken;
    }

    public String createAccessToken(Member member) {
        String accessToken = JWT.create()
                .withSubject("rhinitis_access_token")
                .withExpiresAt(ACCESS_TOKEN_EXPIRES_AT)
                .withClaim("id", member.getMemberId())
                .withClaim("username", member.getUsername())
//                .withClaim("email", email)
                .withClaim("displayName", member.getDisplayName())
//                .withClaim("authorities", List.of(member.getMemberRole().name(), member.getMemberStatus().name()))
                .sign(Algorithm.HMAC512(accessKey));
        accessToken = BEARER_PREFIX + accessToken;
        return accessToken;
    }

    public String createRefreshToken(Member member) {
        String refreshToken = JWT.create()
                .withSubject("rhinitis_refresh_token")
                .withExpiresAt(REFRESH_TOKEN_EXPIRES_AT)
//                .withClaim("id", member.getMemberId())
//                .withClaim("username", member.getUsername())
//                .withClaim("email", email)
//                .withClaim("displayName", member.getDisplayName())
//                .withClaim("authorities", List.of(member.getMemberRole().name(), member.getMemberStatus().name()))
                .sign(Algorithm.HMAC512(refreshKey));
        refreshToken = BEARER_PREFIX + refreshToken;
        return refreshToken;
    }

    public boolean isTokenExpired(DecodedJWT jwt) {
        Date expireDate = jwt.getExpiresAt();
        return expireDate.before(new Date());
    }

    public Map<String, Object> getClaimsFromToken(String token, String keys) {
        DecodedJWT decodedJWT = JWT.decode(token);
        // refresh or access 분기 -> 안 쓰는 듯?
//        String key = keys.equals("refresh") ? refreshKey : accessKey;
        String username = decodedJWT.getClaim("username").asString();
        Long id = decodedJWT.getClaim("id").asLong();
        return Map.of("username", username, "id", id);
    }

    public String getUsernameFromToken(String token){
        return JWT.decode(token).getClaim("username").asString();
    }
    public Long getMemberIdFromToken(String token){
        return JWT.decode(token).getClaim("id").asLong();
    }

    public Long getExpire(String token){
        DecodedJWT decode = JWT.decode(token.replace("Bearer ",""));
        Date exp = decode.getExpiresAt();
        log.info("token expires at {}", exp);
        return exp.getTime()-System.currentTimeMillis();
    }

    public String resolveToken(String token) {
        if (token != null && token.startsWith(BEARER_PREFIX)) {
            return token.replace(BEARER_PREFIX, "");
        }
        return null;
    }
}
