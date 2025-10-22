package com.ktb.community.util.authorization;


import com.ktb.community.domain.User;
import com.ktb.community.repository.UserRepository;
import com.ktb.community.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Authorization {

    private final TokenService tokenService;
    private final UserRepository userRepository;

    public Long extractUserIdFromHeader(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return tokenService.extractUserId(token);
    }

    public User extractUserFromHeader(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        Long userId = tokenService.extractUserId(token);
        return userRepository.findById(userId);
    }
}
