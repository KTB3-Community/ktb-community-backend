package com.ktb.community.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository {

    void save(Long userId, String refreshToken);
    String find(Long userId);
    void delete(Long userId);
}
