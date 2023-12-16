package com.rhinitis.projectrhinitis.config.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RedisUtils {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String HASH_KEY_REFRESH = "refresh";
    private final String HASH_KEY_LOGOUT = "logout";

    public String getRefreshToken(Long memberId) {
        return (String) redisTemplate.opsForHash().get(String.valueOf(memberId), HASH_KEY_REFRESH);
    }

    public void saveRefreshToken(Long memberId, String refreshToken, Date expiresAt) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();
        String key = String.valueOf(memberId);
        log.info("saveRefreshToken key: {}, refreshToken: {}", key, refreshToken);
        hashOperations.put(key, HASH_KEY_REFRESH, refreshToken);
//        redisTemplate.expireAt(key, expiresAt);
    }

    public void deleteRefreshToken(Long memberId) {
        redisTemplate.opsForHash().delete(String.valueOf(memberId), HASH_KEY_REFRESH);
    }

    public void addBlockList(String accessToken, Long exp) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        log.info("exp: {}", exp);
        if (exp > 0) {
            log.info("addBlockList exp: {}", exp);
            valueOperations.set("blk_" + accessToken, HASH_KEY_LOGOUT, exp, TimeUnit.MILLISECONDS);
        }
    }

    public boolean isBlocked(String accessToken) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String value = (String) valueOperations.get("blk_" + accessToken);
        return value != null && !value.isEmpty();
    }
}
