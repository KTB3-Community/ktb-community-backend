package com.ktb.community.util.id;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class AtomicIdRegistry implements IdGenerator {

    private final ConcurrentHashMap<String, AtomicLong> atomicLongConcurrentHashMap = new ConcurrentHashMap<>();

    @Override
    public long next(String domain) {
        return atomicLongConcurrentHashMap.computeIfAbsent(domain, d -> new AtomicLong(1L))
                .getAndIncrement();
    }

    @Override
    public long peek(String domain) {
        return atomicLongConcurrentHashMap.getOrDefault(domain, new AtomicLong(1L)).get();
    }
}
