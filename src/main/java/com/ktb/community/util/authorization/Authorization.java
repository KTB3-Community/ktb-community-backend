package com.ktb.community.util.authorization;


import com.ktb.community.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Authorization {

    private final TokenService tokenService;

    public Long extractUserInfoFromToken(String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");
        return tokenService.extractUserId(token);
    }
}
