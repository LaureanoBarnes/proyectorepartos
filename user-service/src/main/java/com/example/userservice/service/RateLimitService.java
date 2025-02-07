package com.example.userservice.service;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.BucketConfiguration;
import io.github.bucket4j.Refill;
import io.github.bucket4j.redis.lettuce.cas.LettuceBasedProxyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class RateLimitService {

    @Autowired
    private LettuceBasedProxyManager<byte[]> proxyManager;

    public Bucket resolveBucket(String key, String endpoint) {
        String prefixedKey = "rate_limit:" + key + ":" + endpoint; // Agrega prefijo manualmente
        int capacity = endpoint.startsWith("/api/user/create") ? 50 : 100;
        Bandwidth bandwidth = Bandwidth.classic(capacity, Refill.greedy(capacity, Duration.ofMinutes(1)));
        BucketConfiguration config = BucketConfiguration.builder().addLimit(bandwidth).build();

        return proxyManager.builder().build(
                prefixedKey.getBytes(StandardCharsets.UTF_8),
                () -> config
        );
    }
}