package com.ktb.community.repository;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//@Component
//public class InMemoryTokenRepository implements TokenRepository{
//
//    private final Map<Long, String> storage = new ConcurrentHashMap<>();
//
//    @Override
//    public void save(Long userId, String refreshToken) {
//        storage.put(userId, refreshToken);
//    }
//
//    @Override
//    public String find(Long userId) {
//        return storage.get(userId);
//    }
//
//    @Override
//    public void delete(Long userId) {
//        storage.remove(userId);
//    }
//}
