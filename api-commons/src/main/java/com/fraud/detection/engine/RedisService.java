
package com.fraud.detection.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fraud.detection.constants.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean isExist(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    public boolean tryLock(String key, String value, Duration duration) {
        return Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, duration));
    }
}
