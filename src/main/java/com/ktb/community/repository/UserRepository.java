package com.ktb.community.repository;

import com.ktb.community.domain.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository {

    User save(User user);
    User findById(Long userId);
    User findByEmail(String email);
    void delete(Long userId);
}
