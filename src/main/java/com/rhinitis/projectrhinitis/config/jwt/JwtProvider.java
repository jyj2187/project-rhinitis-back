package com.rhinitis.projectrhinitis.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class JwtProvider {
    @Value("${jwt.access}")
    private String accessKey;
//    private String refreshKey;
    @Value("${jwt.header}")
    private String header;
    public String getHeader() {
        return header;
    }

    public String createAccessToken(Long memberId, String username, String displayName) {
        String accessToken = JWT.create()
                .withSubject("rhinitis_access_token")
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 10)))
                .withClaim("id", memberId)
                .withClaim("username", username)
//                .withClaim("email", email)
                .withClaim("displayName", displayName)
                .sign(Algorithm.HMAC512(accessKey));
        accessToken = "Bearer " + accessToken;
        return accessToken;
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
        DecodedJWT decode=JWT.decode(token.replace("Bearer ",""));
        Date exp = decode.getExpiresAt();
        return exp.getTime()-System.currentTimeMillis();
    }
}
