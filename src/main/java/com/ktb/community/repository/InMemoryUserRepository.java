package com.ktb.community.repository;

import com.ktb.community.domain.User;
import com.ktb.community.util.id.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class InMemoryUserRepository implements UserRepository{

    private final IdGenerator idGenerator;
    private final Map<Long, User> storage = new ConcurrentHashMap<>();


    @Override
    public synchronized User save(User user) {
        if (user.getId() == 0L) {
            user = user.withId(idGenerator.next("user"));
        }
        storage.put(user.getId(), user);

        return user;
    }

    @Override
    public User findById(Long userId) {
        return storage.get(userId);
    }

    // 이메일을 기준으로 회원 정보를 저장하는 Map 추가 여부 고민
    @Override
    public User findByEmail(String email) {
        return storage.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void delete(Long userId) {
        storage.remove(userId);
    }


}
