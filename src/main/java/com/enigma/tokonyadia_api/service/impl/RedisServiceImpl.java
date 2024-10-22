package com.enigma.tokonyadia_api.service.impl;

import com.enigma.tokonyadia_api.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void save(String key, String value, Duration duration) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, duration);
    }

    @Override
    public String get(String key) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        return ops.get(key);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public Boolean isExist(String key) {
        return redisTemplate.hasKey(key);
    }
}