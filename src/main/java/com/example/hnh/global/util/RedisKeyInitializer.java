package com.example.hnh.global.util;

import jakarta.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyInitializer {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisKeyInitializer(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void clearKeys() {
        redisTemplate.delete(redisTemplate.keys("board:view:*"));
    }
}
